package com.dahuangit.seobi.receiver.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.dahuangit.seobi.receiver.constant.SeobiProxyTestConstants;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

/**
 * 数据接收服务
 * 
 * @author 黄仁良
 * 
 */
public class DataReceiverControllerTest {
	private static String host = "http://121.14.42.135:9000/seobi/spring/receiver/receiveQQTalkMsg";

	private static final Logger LOG = Log4jUtils.getLogger(SeobiProxyTestConstants.LOG4J_FILE_PATH,
			DataReceiverControllerTest.class);

	public static void main(String[] args) {
		Map<String, String> serialParams = new HashMap<String, String>();

		try {
			String path = SeobiProxyTestConstants.REQ_XML_PATH;
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
