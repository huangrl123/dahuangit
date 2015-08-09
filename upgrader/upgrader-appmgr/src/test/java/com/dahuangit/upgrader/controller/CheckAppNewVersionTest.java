package com.dahuangit.upgrader.controller;

import java.util.HashMap;
import java.util.Map;

import com.dahuangit.util.net.http.HttpKit;

public class CheckAppNewVersionTest {

	public static void main(String[] args) {
		try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("currentAppVersion", "2.20.1");

			String s = HttpKit.getHttpRequestContent("http://192.168.1.107:8080/upgrader/appUpgrade/checkAppNewVersion",
					map, null);

			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
