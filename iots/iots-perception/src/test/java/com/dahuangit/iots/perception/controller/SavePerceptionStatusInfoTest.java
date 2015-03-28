package com.dahuangit.iots.perception.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class SavePerceptionStatusInfoTest {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			FileUploadNoticeTest.class);

	public static void main(String[] args) {
		try {

			log.debug("正在请求...");
			StringBuffer sb = new StringBuffer();
			sb.append("<request>");
			sb.append("  <perception-param>");
			sb.append("    <param-id>202</param-id>");
			sb.append("    <param-value>1</param-value>");
			sb.append("    <param-desc>232告警</param-desc>");
			sb.append("  </perception-param>");
			sb.append("</request>");
			Map<String, String> map = new HashMap<String, String>();
			map.put("perceptionAddr", "huizhoueden");
			map.put("perceptionStatusInfoXml", sb.toString());

			 String s =	 HttpKit.getHttpRequestContent("http://120.24.86.107:8080/iots/perception/savePerceptionStatusInfo",
			//String s = HttpKit.getHttpRequestContent("http://localhost:8080/iots/perception/savePerceptionStatusInfo",
					map, null);

			log.debug("测试完毕，返回报文:" + XmlUtils.formatXMLStr(s));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
