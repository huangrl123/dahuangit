package com.dahuangit.iots.perception.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class RemoteQueryMachineTest {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			RemoteQueryMachineTest.class);

	public static void main(String[] args) {
		try {

			log.debug("远程查询测试...");
			Map<String, String> map = new HashMap<String, String>();
			map.put("machineAddr", "DSFE432EWR");

//			String s = HttpKit.getHttpRequestContent("http://localhost:8080/iots/spring/perception/remoteQueryMachine",
//					map, null);
			String s = HttpKit.getHttpRequestContent("http://120.24.86.107:8080/iots/spring/perception/remoteQueryMachine",
					map, null);

			log.debug("远程查询测试返回报文:" + XmlUtils.formatXMLStr(s));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
