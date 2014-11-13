package com.dahuangit.seobi.analyzer.service;

public interface AnalyzeService {
	/**
	 * 获取百度原创度
	 * 
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	double getBaiduOriginarityPercent(String content) throws Exception;

	/**
	 * 分析本系统数据库表中qq说说的百度原创度
	 */
	void analyzeQQTalkMsgBaiduOriginatyPercent();
}
