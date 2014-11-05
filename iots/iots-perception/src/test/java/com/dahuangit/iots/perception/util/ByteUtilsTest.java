package com.dahuangit.iots.perception.util;

import org.apache.log4j.Logger;

import com.dahuangit.iots.perception.tcpclient.PerceptionClient;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.log.Log4jUtils;

public class ByteUtilsTest {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties", PerceptionClient.class);

	public static void main(String[] args) {
		log.debug(ByteUtils.byteArrToHexString(ByteUtils.intToByteArray(2)));
	}
}
