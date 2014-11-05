package com.dahuangit.seobi.receiver;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.dahuangit.util.net.HttpKit;

public class Main {
	public static void main(String[] args) {

		try {
			String s = FileUtils.readFileToString(new File("c:/qqtalk.txt"));

			HttpKit.doHttpRequest("http://localhost:8080/seobi/spring/receiver/receiveQQTalkMsg",
					new HashMap<String, String>().put("qqTalkMsgs", s));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
