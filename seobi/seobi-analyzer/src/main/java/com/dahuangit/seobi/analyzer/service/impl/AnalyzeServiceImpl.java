package com.dahuangit.seobi.analyzer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.seobi.analyzer.dao.RelatedSearchKeyDao;
import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;
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
	private RelatedSearchKeyDao relatedSearchKeyDao = null;

	public void analyzeBaiduOriginarityPercent() {
		// 搜索所有未搜索过的说说信息
		List<QQTalkMsg> notSearchedList = this.qqTalkMsgDao.getAllNotAnalyzedQQTalkMsg();

		for (QQTalkMsg msg : notSearchedList) {
			String content = msg.getTalkContent();
			double originalityPercent = getBaiduOriginarityPercent(content, msg);

			// 保存说说信息(无需手动保存，系统会自动保存)
			msg.setAnalyzed(true);
			msg.setAnalyzeTime(new Date());
			msg.setOriginalityPercent(originalityPercent);
		}
	}

	public double getBaiduOriginarityPercent(String content) {
		return this.getBaiduOriginarityPercent(content, null);
	}

	private double getBaiduOriginarityPercent(String content, QQTalkMsg msg) {
		int minSimilaryCount = 0;

		try {
			log.debug("正在通过百度搜索说说内容,说说内容为:[" + content + "]");

			// 如果说说内容没有超长
			if (content.length() < BAIDU_SEARCH_KEY_COUNT) {
				minSimilaryCount = saveKeyAndCountSimilarity(content, msg);
				// 如果搜索的内容大于百度要求的搜索字数，则分段搜索
			} else {
				double d = (double) content.length() / (double) BAIDU_SEARCH_KEY_COUNT;
				int sectionCount = (int) Math.ceil(d);

				// 遍历每一小段
				List<Integer> list = new ArrayList<Integer>();
				for (int i = 1; i <= sectionCount; i++) {
					String key = content.substring((i - 1) * BAIDU_SEARCH_KEY_COUNT, BAIDU_SEARCH_KEY_COUNT - 1);

					if (key.length() < BAIDU_LAST_SECTION_MIN_LENGTH) {
						continue;
					}

					int min = saveKeyAndCountSimilarity(key, msg);
					list.add(min);
				}

				minSimilaryCount = SortUtils.getMin(list);
			}

			double originalityPercent = (double) minSimilaryCount / (double) BAIDU_SEARCH_KEY_COUNT;

			log.debug("说说:[" + content + "] 原创度分析完毕，其原创度为：[" + NumberUtils.number2percent(originalityPercent) + "]");

		} catch (Exception e) {
			log.error("通过百度搜索说说内容时发生错误，说说内容为:[" + content + "],跳过本条记录，继续执行后面的记录");
			e.printStackTrace();
		}

		return minSimilaryCount;
	}

	private int saveKeyAndCountSimilarity(String shuoshuoContent, QQTalkMsg msg) throws IOException, DocumentException {

		String result = BaiduUtils.searchByKey(shuoshuoContent);

		// 保存关联搜索关键字
		String startStr = "<div id=\"rs\">";
		String endStr = "id=\"page\"";
		int start = result.indexOf(startStr);
		int end = result.indexOf(endStr);

		if (null != msg) {
			saveKey(result, msg);
		}

		// 搜索返回条目
		startStr = "<div class=\"nums\">";
		endStr = "<div id=\"rs\">";
		start = result.indexOf(startStr) + startStr.length();
		end = result.indexOf(endStr);

		String searchResult = result.substring(start, end);
		startStr = "</div>";
		start = searchResult.indexOf(startStr) + startStr.length();

		searchResult = searchResult.substring(start);
		searchResult = searchResult.replaceAll("&", "");
		searchResult = searchResult.replaceAll("data-nolog", "");
		searchResult = "<div>" + searchResult + "</div>";

		String searchResultxpathExpression = "div[1]/div";
		List<Node> results = XpathUtils.findNodes(searchResult, searchResultxpathExpression);

		// 遍历每一个结果
		List<Integer> list = new ArrayList<Integer>();
		for (Node n : results) {
			int d = resultSimilarityCount(shuoshuoContent, n);
			list.add(d);
		}

		int min = SortUtils.getMin(list);
		return min;
	}

	private int resultSimilarityCount(String shuoshuoContent, Node resultNode) throws DocumentException {

		Element e = (Element) resultNode;

		// 查询出每个结果中飘红内容
		String searchResultxpathExpression = "em";
		String resultItemStr = e.asXML();
		List<Node> emNodes = XpathUtils.findNodes(resultItemStr, searchResultxpathExpression);

		boolean isAppend = false;
		Map<String, Integer> currentSections = new HashMap<String, Integer>();
		List<Integer> continuousTxtCountList = new ArrayList<Integer>();

		int appendLength = 0;
		for (int i = 0; i < emNodes.size(); i++) {
			Node em = emNodes.get(i);
			String emText = em.getText();

			if (i == 0) {
				continuousTxtCountList.add(emText.length());
				continue;
			}

			String[] arr = resultItemStr.split("<em>" + emText + "</em>");
			String s = null;

			// 取出当前进行到哪段了
			int sectionCount = 0;

			if (null != currentSections.get(emText)) {
				sectionCount = currentSections.get(emText);
			}

			if (arr.length == 1) {
				s = arr[0];
			} else {
				s = arr[sectionCount + 1];
			}

			// 检查飘红内容中的前一段有没有“的、地、得...”等字，如果有就连在一起,反正只要隔着一个字符都算,将当做是连续的
			if (s.indexOf("<em>") == s.length() - 7 && s.indexOf("</em>") == s.length() - 5) {
				isAppend = true;
			}

			if (isAppend) {
				appendLength = appendLength + emText.length();
				if (i == emNodes.size() - 1) {
					continuousTxtCountList.add(appendLength);
					break;
				}
			} else {
				continuousTxtCountList.add(appendLength);
				continuousTxtCountList.add(emText.length());
				appendLength = 0;
			}

			currentSections.put(emText, i);
		}

		// 取飘红内容长度的最小的一个
		int min = SortUtils.getMin(continuousTxtCountList);
		return min;
	}

	private void saveKey(String result, QQTalkMsg msg) throws DocumentException {
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

		for (Node n : nodes) {
			String content = n.getText();
			RelatedSearchKey relatedSearchKey = new RelatedSearchKey();
			relatedSearchKey.setQqTalkMsg(msg);
			relatedSearchKey.setRelatedSearchKey(content);
			relatedSearchKeyDao.add(relatedSearchKey);

			log.debug("相关搜索关键字保存，关键字:[" + content + "]");
		}
	}

}
