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
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.seobi.analyzer.dao.TalkMsgRelatedSearchKeyDao;
import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;
import com.dahuangit.seobi.analyzer.entry.TalkRelatedSearchKey;
import com.dahuangit.seobi.analyzer.service.AnalyzeService;
import com.dahuangit.seobi.analyzer.util.BaiduUtils;
import com.dahuangit.seobi.receiver.dao.QQTalkMsgDao;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;
import com.dahuangit.util.NumberUtils;
import com.dahuangit.util.SortUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.xml.XpathUtils;

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
	private TalkMsgRelatedSearchKeyDao msgRelatedSearchKeyDao = null;

	/**
	 * 分析本系统数据库表中qq说说的百度原创度
	 */
	public void analyzeQQTalkMsgBaiduOriginatyPercent() {
		// 搜索所有未搜索过的说说信息
		List<QQTalkMsg> notSearchedList = this.qqTalkMsgDao.getAllNotAnalyzedQQTalkMsg();

		for (QQTalkMsg msg : notSearchedList) {
			String content = msg.getTalkContent();
			Double originalityPercent = null;
			List<RelatedSearchKey> keys = null;

			try {
				originalityPercent = getBaiduOriginarityPercent(content);
				keys = parseAndGetRelatedSearchKey(content);
			} catch (Exception e) {
			}

			// 保存说说信息(无需手动保存，系统会自动保存)
			msg.setAnalyzed(true);
			msg.setAnalyzeTime(new Date());
			if (null != originalityPercent) {
				msg.setOriginalityPercent(originalityPercent);
			}

			if (null != keys) {
				TalkRelatedSearchKey talkRelatedSearchKey = new TalkRelatedSearchKey();
				talkRelatedSearchKey.setQqTalkMsg(msg);
				talkRelatedSearchKey.setRelatedSearchKeys(keys);
				this.msgRelatedSearchKeyDao.add(talkRelatedSearchKey);
			}
		}
	}

	/**
	 * 获取百度原创度
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public Double getBaiduOriginarityPercent(String content) throws Exception {
		Validate.notNull(content, "需要计算原创度的字符不能为null");

		Double baiduSimilarityPercent = null;
		Double baiduOriginatyPercent = null;

		try {
			log.debug("正在通过百度搜索相关内容:[" + content + "]");

			// 滤掉表情符号,例如： [em]e110042[/em]
			String regEx = "\\[em\\]e[0-9]+?\\[\\/em\\]";
			content = content.replaceAll(regEx, "");

			// 如果内容没有超长
			if (content.length() < BAIDU_SEARCH_KEY_COUNT) {
				Double baiduSimilarityLength = saveKeyAndCountSectionSimilarityLength(content);

				baiduSimilarityPercent = baiduSimilarityLength / (double) content.length();
				// 如果搜索的内容大于百度要求的搜索字数，则分段搜索
			} else {
				Double d = (double) content.length() / (double) BAIDU_SEARCH_KEY_COUNT;
				int sectionCount = (int) Math.ceil(d);

				// 遍历每一小段
				List<Double> sectionSimilaryList = new ArrayList<Double>();
				for (int i = 1; i <= sectionCount; i++) {
					String key = content.substring((i - 1) * BAIDU_SEARCH_KEY_COUNT, BAIDU_SEARCH_KEY_COUNT - 1);

					if (key.length() < BAIDU_LAST_SECTION_MIN_LENGTH) {
						continue;
					}

					// 这段的飘红字数的平均长度
					Double sectionSimilaryCountLength = saveKeyAndCountSectionSimilarityLength(key);
					Double sectionSimilary = sectionSimilaryCountLength / (double) key.length();

					sectionSimilaryList.add(sectionSimilary);
				}

				// 得到每一小段内容的平均相似度
				baiduSimilarityPercent = SortUtils.getAverage(sectionSimilaryList);
			}

			baiduOriginatyPercent = 1 - baiduSimilarityPercent;

			log.debug("内容:[" + content + "]相似度为：[" + NumberUtils.number2percent(baiduSimilarityPercent) + "]");
			log.debug("内容:[" + content + "]原创度为：[" + NumberUtils.number2percent(baiduOriginatyPercent) + "]");
			log.debug("内容:[" + content + "]原创度分析完毕!");
		} catch (Exception e) {
			log.error("通过百度搜索内容时发生错误，内容为:[" + content + "],跳过本条记录，继续执行后面的记录");
			e.printStackTrace();
			throw e;
		}

		return baiduOriginatyPercent;
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
	private Double saveKeyAndCountSectionSimilarityLength(String sectionStr) throws IOException, DocumentException {

		String result = BaiduUtils.searchByKey(sectionStr);

		String startStr = "<div class=\"nums\">";
		String endStr = "<div id=\"rs\">";
		int start = result.indexOf(startStr) + startStr.length();
		int end = result.indexOf(endStr);

		String searchResult = result.substring(start, end);
		startStr = "</div>";
		start = searchResult.indexOf(startStr) + startStr.length();

		searchResult = searchResult.substring(start);
		searchResult = searchResult.replaceAll("&", "");
		searchResult = searchResult.replaceAll("data-nolog", "");
		searchResult = "<div>" + searchResult + "</div>";

		String searchResultxpathExpression = "div[1]/div";
		List<Node> results = XpathUtils.findNodes(searchResult, searchResultxpathExpression);

		// 遍历一段的每一个结果
		List<Double> list = new ArrayList<Double>();
		for (Node n : results) {
			Double d = resultItemSimilarityLength(n);
			list.add(d);
		}

		Double average = SortUtils.getAverage(list);

		return average;
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
		end = relatedSearchStr.indexOf(endStr);
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

}
