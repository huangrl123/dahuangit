package com.dahuangit.iots.perception.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class FileUploadNoticeTest {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			FileUploadNoticeTest.class);

	public static void main(String[] args) {
		try {

			log.debug("正在请求...");
			StringBuffer sb = new StringBuffer();
			sb.append("<request>");
			sb.append("  <perception-addr>DSFE432EWR</perception-addr>");
			sb.append("  <file>");
			sb.append("    <file-path>/DSFE432EWR/2014/11/30/</file-path>");
			sb.append("    <file-name>2014-11-30-091244.mp4</file-name>");
			sb.append("  </file>");
			sb.append("  <file>");
			sb.append("    <file-path>/DSFE432EWR/2014/11/30/</file-path>");
			sb.append("    <file-name>2014-11-30-091122.mp4</file-name>");
			sb.append("  </file>");
			sb.append("  <file>");
			sb.append("    <file-path>/DSFE432EWR/2014/11/30/</file-path>");
			sb.append("    <file-name>2014-11-30-092044.mp4</file-name>");
			sb.append("  </file>");
			sb.append("</request>");
			Map<String, String> map = new HashMap<String, String>();
			map.put("fileInfoXml", sb.toString());

			String s = HttpKit.getHttpRequestContent("http://localhost:8080/iots/spring/perception/fileUploadNotice",
					map, null);

			// String s =
			// HttpKit.getHttpRequestContent("http://120.24.86.107:8080/iots/spring/perception/remoteCtrlMachine",
			// map, null);

			log.debug("测试完毕，返回报文:" + XmlUtils.formatXMLStr(s));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
