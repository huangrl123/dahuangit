package com.dahuangit.seobi.proxy.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class ProxyControllerReceiverTest {

	private static String host = "http://localhost:8080/seobi/spring/proxyMgr/addProxy";

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			ProxyControllerReceiverTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {
			serialParams.put("ip", "183.207.228.123");
			serialParams.put("port", "80");

			String result = HttpKit.getHttpRequestContent(host, serialParams, null);

			log.debug(XmlUtils.formatXMLStr(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
