package com.dahuangit.util.string;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import jodd.util.collection.CharArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * 字符串工具类
 * 
 * @author 黄仁良
 * 
 */
public class StringUtils2 extends StringUtils {
	/**
	 * 将字符串转换为utf8 二进制流
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toUTF8Byte(String str) throws UnsupportedEncodingException {
		Validate.notNull(str, "传入字符串不能为null");

		return str.getBytes("UTF-8");
	}

	/**
	 * 将驼峰状字符串转化为下划线形式字符串<br>
	 * 例如：userNo 转换之后为USER_NO
	 * 
	 * @param str
	 * @return
	 */
	public static String hump2underlineUpperCase(String str) {
		Validate.notNull(str, "传入字符串不能为null");

		char[] carr = str.toCharArray();

		CharArrayList list = new CharArrayList();
		for (int i = 0; i < carr.length; i++) {
			char c = carr[i];

			if (Character.isUpperCase(c)) {
				if (i != 0 && !Character.isUpperCase(carr[i - 1])) {
					c = Character.toLowerCase(c);
					list.add('_');
				}
			}

			list.add(c);
		}

		return new String(list.toArray()).toUpperCase();
	}

	/**
	 * 将驼峰状字符串转化为castor形式字符串，例如:userName===>user-name
	 * 
	 * @param str
	 * @return
	 */
	public static String hump2CastorStyle(String str) {
		Validate.notNull(str, "传入字符串不能为null");

		char[] carr = str.toCharArray();

		CharArrayList list = new CharArrayList();
		for (int i = 0; i < carr.length; i++) {
			char c = carr[i];

			if (Character.isUpperCase(c)) {
				if (i == 0) {// 整个单词的首字母变小写
					c = Character.toLowerCase(c);
				}

				if (i != 0 && !Character.isUpperCase(carr[i - 1])) {
					c = Character.toLowerCase(c);
					list.add('-');
				}
			}

			list.add(c);
		}

		return new String(list.toArray());
	}

	/**
	 * 将查询字符串转换为hashMap<br>
	 * 例如:queryType:1;customerNo:1001
	 * 转换之后为:key:queryType,value:1;key:customerNo,value:1001
	 * 
	 * @param queryString
	 *            查询字符串
	 * @return
	 */
	public static Map<String, String> queryString2map(String queryString) {
		Validate.notNull(queryString, "查询字符串不能为空");

		Map<String, String> map = new HashMap<String, String>();
		String[] kvStrs = queryString.split(";");
		for (String kvStr : kvStrs) {
			String[] kv = kvStr.split(":");

			if (null == kv[0] || kv[0].equals("") || kv.length < 2) {
				continue;
			}

			if (null != kv[1]) {
				kv[1] = kv[1].trim();
			}

			map.put(kv[0].trim(), kv[1]);
		}
		return map;
	}

	/**
	 * 将以逗号分隔的ID字符串转化为List<String>形式
	 * 
	 * @param commaStr
	 * @return
	 */
	public static List<String> commaStrToList(String commaStr) {
		List<String> list = new ArrayList<String>();
		String[] arr = commaStr.split(",");
		for (String s : arr) {
			if (null != s && !"".equals(s.trim())) {
				s = s.trim();
				list.add(s);
			}
		}
		return list;
	}
}
