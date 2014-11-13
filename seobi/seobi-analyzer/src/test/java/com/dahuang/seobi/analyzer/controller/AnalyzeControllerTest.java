package com.dahuang.seobi.analyzer.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Node;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.util.NumberUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;
import com.dahuangit.util.xml.XpathUtils;

public class AnalyzeControllerTest extends BaseController {
	private static String host = "http://localhost:8080/seobi/spring/analyze/doAnalyze";

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			AnalyzeControllerTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {
			String content = "今天天气不错";

			serialParams.put("content", content);
			String result = HttpKit.getHttpRequestContent(host, serialParams, null);

			log.debug("服务端返回报文:" + XmlUtils.formatXMLStr(result));

			Node n = XpathUtils.findUnique(result, "/response/originarity-percent");

			String s = n.getText();

			Double d = Double.parseDouble(s);

			String percentStr = NumberUtils.number2percent(d);

			log.debug("内容：[" + content + "]" + "  原创度为:[" + percentStr + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
