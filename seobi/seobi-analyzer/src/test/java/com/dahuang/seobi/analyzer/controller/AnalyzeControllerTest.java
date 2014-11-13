package com.dahuang.seobi.analyzer.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class AnalyzeControllerTest extends BaseController {
	private static String host = "http://localhost:8080/seobi/spring/analyze/doAnalyze";

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			AnalyzeControllerTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {

			String result = HttpKit.getHttpRequestContent(host, null, null);

			log.debug(XmlUtils.formatXMLStr(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
