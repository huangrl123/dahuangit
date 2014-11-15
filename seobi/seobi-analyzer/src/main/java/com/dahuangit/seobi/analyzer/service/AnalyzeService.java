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
	Double getBaiduOriginarityPercent(String content) throws Exception;

	/**
	 * 分析本系统数据库表中qq说说的百度原创度
	 */
	void analyzeQQTalkMsgBaiduOriginatyPercent();

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qqTalkMsgs
	 * @return
	 */
	QQTalkMsg analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(Integer qqTalkMsgId);

	/**
	 * 分析并且获取qq说说原创度
	 * 
	 * @param qQtalkMsgIds
	 * @return
	 */
	List<QQTalkMsg> analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(List<Integer> qQtalkMsgIds);
}
