package com.dahuangit.seobi.analyzer.service;

import java.util.List;

import com.dahuangit.seobi.receiver.entry.QQTalkMsg;

public interface AnalyzeService {
	/**
	 * 获取百度原创度
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	Double getBaiduOriginarityPercentByStr(String content) throws Exception;

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qqTalkMsgs
	 * @return
	 */
	void analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(Integer qqTalkMsgId);

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qQtalkMsgIds
	 * @return
	 */
	public void analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(List<Integer> qQtalkMsgIds);
}
