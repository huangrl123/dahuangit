package com.dahuangit.seobi.receiver.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

/**
 * 数据接收服务
 * 
 * @author 黄仁良
 * 
 */
public class DataReceiverControllerTest {
	private static String host = "http://localhost:8080/seobi/spring/receiver/receiveQQTalkMsg";

	private static final Logger LOG = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			DataReceiverControllerTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {
			String path = "E:\\dahuang_workspace\\seobi\\seobi-receiver\\src\\test\\resources\\req-eg.xml";
			String value = FileUtils.readFileToString(new File(path));
			serialParams.put("qqTalkMsgs", value);

			String result = HttpKit.getHttpRequestContent(host, serialParams, null);

			LOG.debug(XmlUtils.formatXMLStr(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
