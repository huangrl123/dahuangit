package com.dahuangit.iots.perception.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMap {

	public static void main(String[] args) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");

		list.add(map);

		Map<String, String> map1 = list.get(0);

		map1.put("1", "2");

		System.out.println(list.get(0).get("1"));
	}

}
