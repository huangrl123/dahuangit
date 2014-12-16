package com.dahuangit.seobi.analyzer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.seobi.analyzer.dao.RelatedSearchKeyDao;
import com.dahuangit.seobi.analyzer.dto.OriginarityPercentDto;
import com.dahuangit.seobi.analyzer.dto.SectionSimilarityLengthDto;
import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;
import com.dahuangit.seobi.analyzer.service.AnalyzeService;
import com.dahuangit.seobi.analyzer.util.SearchUtils;
import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.seobi.proxy.service.ProxyService;
import com.dahuangit.seobi.receiver.dao.QQTalkMsgDao;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;
import com.dahuangit.util.NumberUtils;
import com.dahuangit.util.SortUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.xml.XpathUtils;

@Component
@Transactional
public class AnalyzeServiceImpl implements AnalyzeService {

	private static final Logger log = Log4jUtils.getLogger(AnalyzeServiceImpl.class);

	/** 百度搜索字数 */
	private static final int BAIDU_SEARCH_KEY_COUNT = 38;

	/** 百度分段搜索中，最后一段字数小于该值则不在计算本小段的原创度 */
	private static final int BAIDU_LAST_SECTION_MIN_LENGTH = 4;

	@Autowired
	private QQTalkMsgDao qqTalkMsgDao = null;

	@Autowired
	private RelatedSearchKeyDao relatedSearchKeyDao = null;

	@Autowired
	private ProxyService proxyService = null;

	@Value("${analyzer.useProxy}")
	private boolean useProxy;

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qqTalkMsgs
	 * @return
	 */
	public void analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(Integer qqTalkMsgId) {

		QQTalkMsg msg = this.qqTalkMsgDao.getQQTalkMsg(qqTalkMsgId);

		this.analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(msg.getTalkContent(), msg.getTmId());
	}

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qQtalkMsgIds
	 * @return
	 */
	public void analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(List<Integer> qQtalkMsgIds) {
		List<QQTalkMsg> list = this.qqTalkMsgDao.findByIds(qQtalkMsgIds);

		if (null == list || list.isEmpty()) {
			return;
		}

		for (QQTalkMsg qm : list) {
			Double originalityPercent = this.analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(qm.getTalkContent(),
					qm.getTmId());

			qm.setAnalyzed(true);
			qm.setAnalyzeTime(new Date());

			if (null != originalityPercent) {
				qm.setOriginalityPercent(originalityPercent);
			}

			this.qqTalkMsgDao.update(qm);
		}

	}

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param msg
	 * @return
	 */
	public Double analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(String content, Integer tmId) {
		Validate.notNull(content, "分析并且获取qq说说原创度的qq说说内容不能为null");

		Double originalityPercent = null;
		List<RelatedSearchKey> keys = null;
		OriginarityPercentDto originarityPercentDto = null;

		try {
			originarityPercentDto = getBaiduOriginarityPercent(content);
			originalityPercent = originarityPercentDto.getOriginarityPercent();
			keys = originarityPercentDto.getKeys();
		} catch (Exception e) {
		}

		if (null != keys) {
			for (RelatedSearchKey relatedSearchKey : keys) {
				relatedSearchKey.setTalkId(tmId);
				this.relatedSearchKeyDao.add(relatedSearchKey);
			}

		}

		return originalityPercent;
	}

	/**
	 * 获取百度原创度
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public OriginarityPercentDto getBaiduOriginarityPercent(String content) throws Exception {
		Validate.notNull(content, "需要计算原创度的字符不能为null");

		Double baiduSimilarityPercent = null;
		Double baiduOriginatyPercent = null;

		List<RelatedSearchKey> keys = new ArrayList<RelatedSearchKey>();

		try {
			log.debug("正在通过百度搜索相关内容:[" + content + "]");

			// 滤掉表情符号,例如： [em]e110042[/em]
			String regEx = "\\[em\\]e[0-9]+?\\[\\/em\\]";
			content = content.replaceAll(regEx, "").trim();

			if ("".equals(content)) {
				return null;
			}

			// 如果内容没有超长
			if (content.length() < BAIDU_SEARCH_KEY_COUNT) {
				SectionSimilarityLengthDto dto = saveKeyAndCountSectionSimilarityLength(content);

				if (null != dto && null != dto.getKeys()) {
					keys.addAll(dto.getKeys());
				}

				Double baiduSimilarityLength = dto.getSectionSimilarityLength();

				baiduSimilarityPercent = baiduSimilarityLength / (double) content.length();
				// 如果搜索的内容大于百度要求的搜索字数，则分段搜索
			} else {
				Double d = (double) content.length() / (double) BAIDU_SEARCH_KEY_COUNT;
				int sectionCount = (int) Math.ceil(d);

				// 遍历每一小段
				List<Double> sectionSimilaryList = new ArrayList<Double>();
				for (int i = 1; i <= sectionCount; i++) {
					// 如果其中的一段出错不会导致整个内容测试失败
					log.debug("正在计算第[" + i + "/" + sectionCount + "]段内容的相似度...");
					try {
						String key = "";
						if (i == sectionCount) {
							key = content.substring(BAIDU_SEARCH_KEY_COUNT * (i - 1));
						} else {
							key = content.substring(BAIDU_SEARCH_KEY_COUNT * (i - 1), BAIDU_SEARCH_KEY_COUNT * i);
						}

						if (key.length() < BAIDU_LAST_SECTION_MIN_LENGTH) {
							continue;
						}

						// 这段的飘红字数的平均长度
						SectionSimilarityLengthDto dto = saveKeyAndCountSectionSimilarityLength(key);

						if (null != dto && null != dto.getKeys()) {
							keys.addAll(dto.getKeys());
						}

						Double sectionSimilaryCountLength = dto.getSectionSimilarityLength();

						Double sectionSimilary = sectionSimilaryCountLength / (double) key.length();

						log.debug("第[" + i + "/" + sectionCount + "]段内容的相似度计算完毕,相似度为:["
								+ NumberUtils.number2percent(sectionSimilary));
						sectionSimilaryList.add(sectionSimilary);
					} catch (Exception e) {
						log.debug("正在计算第[" + i + "/" + sectionCount + "]段内容的相似度计算时发生错误，错误信息:" + e.getMessage());
						e.printStackTrace();
					}
				}

				// 得到每一小段内容的平均相似度
				baiduSimilarityPercent = SortUtils.getAverage(sectionSimilaryList);
			}

			baiduOriginatyPercent = 1 - baiduSimilarityPercent;

			log.debug("内容:[" + content + "]相似度为：[" + NumberUtils.number2percent(baiduSimilarityPercent) + "]");
			log.debug("内容:[" + content + "]原创度为：[" + NumberUtils.number2percent(baiduOriginatyPercent) + "]");
			log.debug("内容:[" + content + "]原创度分析完毕!");
		} catch (Exception e) {
			log.error("通过百度搜索内容时发生错误，内容为:[" + content + "],跳过本条记录，继续执行后面的记录,错误信息:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		OriginarityPercentDto originarityPercentDto = new OriginarityPercentDto();
		originarityPercentDto.setOriginarityPercent(baiduOriginatyPercent);
		originarityPercentDto.setKeys(keys);

		return originarityPercentDto;
	}

	/**
	 * 计算百度每一小段的相似字长度
	 * 
	 * @param sectionStr
	 * @param msg
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	private SectionSimilarityLengthDto saveKeyAndCountSectionSimilarityLength(String sectionStr) throws IOException,
			DocumentException {

		String result = null;

		if (useProxy) {
			Proxy p = this.proxyService.getOptimalProxy("GET");
			result = SearchUtils.searchByKey(p, sectionStr);
		} else {
			result = SearchUtils.searchByKey(null, sectionStr);
		}

		if (null == result) {
			return null;
		}

		String startStr = "<div class=\"nums\">";
		String endStr = "<div id=\"rs\">";
		int start = result.indexOf(startStr) + startStr.length();

		int end = result.indexOf(endStr);

		// 如果为-1，则表示相关搜索不为空
		if (-1 == end) {
			// 注意：后面有一个空格!
			endStr = "<p id=\"page\" >";
			end = result.indexOf(endStr);
		}

		String searchResult = result.substring(start, end);
		startStr = "</div>";
		start = searchResult.indexOf(startStr) + startStr.length();

		searchResult = searchResult.substring(start);
		searchResult = searchResult.replaceAll("&", "");
		searchResult = searchResult.replaceAll("data-nolog", "");
		searchResult = "<div>" + searchResult + "</div>";

		String searchResultxpathExpression = "div[1]/div";
		List<Node> results = null;
		try {
			results = XpathUtils.findNodes(searchResult, searchResultxpathExpression);
		} catch (Exception e) {
			log.error("xml解析报错,searchResult=[" + searchResult + "] searchResultxpathExpression=["
					+ searchResultxpathExpression + "]");
			e.printStackTrace();
			return null;
		}

		// 遍历一段的每一个结果
		List<Double> list = new ArrayList<Double>();
		for (Node n : results) {
			Double d = resultItemSimilarityLength(n);
			list.add(d);
		}

		Double average = SortUtils.getAverage(list);
		List<RelatedSearchKey> keys = this.parseAndGetRelatedSearchKey(result);

		SectionSimilarityLengthDto dto = new SectionSimilarityLengthDto();
		dto.setSectionSimilarityLength(average);
		dto.setKeys(keys);

		return dto;
	}

	/**
	 * 计算每条结果的相似字长度
	 * 
	 * @param shuoshuoContent
	 * @param resultNode
	 * @return
	 * @throws DocumentException
	 */
	private Double resultItemSimilarityLength(Node resultItemNode) throws DocumentException {

		Element e = (Element) resultItemNode;

		Double average = null;

		// 查询出每个结果中飘红内容
		String searchResultxpathExpression = "//em";
		String resultItemStr = e.asXML();
		List<Node> emNodes = XpathUtils.findNodes(resultItemStr, searchResultxpathExpression);

		if (null != emNodes && emNodes.size() > 0) {
			List<Double> continuousTxtCountList = new ArrayList<Double>();

			for (int i = 0; i < emNodes.size(); i++) {
				Node em = emNodes.get(i);
				String emText = em.getText();
				continuousTxtCountList.add((double) emText.length());
			}

			average = SortUtils.getAverage(continuousTxtCountList);
		}

		return average;
	}

	/**
	 * 解析并且获取相关搜索的关键字
	 * 
	 * @param result
	 * @return
	 * @throws DocumentException
	 */
	private List<RelatedSearchKey> parseAndGetRelatedSearchKey(String result) throws DocumentException {

		// 保存关联搜索关键字
		String startStr = "<div id=\"rs\">";
		String endStr = "id=\"page\"";
		int start = result.indexOf(startStr);
		int end = result.indexOf(endStr);

		String relatedSearchStr = result.substring(start, end);

		startStr = "<div id=\"rs\"><div class=\"tt\">相关搜索</div>";
		endStr = "</div><p";
		start = relatedSearchStr.indexOf(startStr) + startStr.length();
		if (-1 == start) {
			return null;
		}

		end = relatedSearchStr.indexOf(endStr);
		if (-1 == end) {
			return null;
		}

		relatedSearchStr = relatedSearchStr.substring(start, end);

		relatedSearchStr = relatedSearchStr.replaceAll("&", "");
		relatedSearchStr = relatedSearchStr.replaceAll("</tr></table>", "</table>");

		String xpathExpression = "//tr//th//a";
		List<Node> nodes = XpathUtils.findNodes(relatedSearchStr, xpathExpression);

		List<RelatedSearchKey> relatedSearchKeys = new ArrayList<RelatedSearchKey>();
		for (Node n : nodes) {
			String content = n.getText();
			RelatedSearchKey relatedSearchKey = new RelatedSearchKey();
			relatedSearchKey.setRelatedSearchKey(content);

			relatedSearchKeys.add(relatedSearchKey);

			log.debug("相关搜索关键字保存，关键字:[" + content + "]");
		}

		return relatedSearchKeys;
	}

	@Override
	public Double getBaiduOriginarityPercentByStr(String content) throws Exception {
		OriginarityPercentDto dto = this.getBaiduOriginarityPercent(content);
		return dto.getOriginarityPercent();
	}

}
