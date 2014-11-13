package com.dahuangit.seobi.analyzer.util;

import java.io.IOException;
import java.net.URLEncoder;

import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.string.StringUtils2;

public class BaiduUtils {

	private static final String BAIDU_SEARCH_BASE_URL = "http://www.baidu.com/s?wd=%s&rn=20&lm=0";

	public static String searchByKey(String key) throws IOException {
		key = URLEncoder.encode(key);
		
		String url = StringUtils2.StringFormat(BAIDU_SEARCH_BASE_URL, key);

		String result = HttpKit.doHttpRequest(url);

		return result;
	}
}
