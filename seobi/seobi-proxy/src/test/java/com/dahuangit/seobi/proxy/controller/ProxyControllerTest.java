package com.dahuangit.seobi.proxy.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class ProxyControllerTest {

	private static String host = "http://localhost:8080/seobi/spring/receiver/receiveQQTalkMsg";

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			ProxyControllerTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {
			serialParams.put("requestStr", "<request><url>http://www.baidu.com</url><encode>UTF-8</encode></request>");

			String result = HttpKit.getHttpRequestContent(host, serialParams, null);

			log.debug(XmlUtils.formatXMLStr(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
