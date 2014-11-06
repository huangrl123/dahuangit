package com.dahuangit.util.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.message.BasicHeader;

/**
 * 将客户端传过来的字符解析为http头信息
 * 
 * @author dahuang
 * 
 *         创建时间2014年11月6日下午3:27:22
 */
public class HttpHeaderParser {

	public static HttpHeaderInfo parse(String headerMsg) throws IOException {
		HttpHeaderInfo httpHeaderInfo = new HttpHeaderInfo();

		StringReader in = new StringReader(headerMsg);
		BufferedReader br = new BufferedReader(in);

		String line = null;
		boolean readedFirstLine = false;
		Map<String, String> map = new HashMap<String, String>();
		String url = null;
		while (null != (line = br.readLine())) {
			if (!readedFirstLine) {
				String[] first = line.split(" ");

				String method = first[0].trim();
				url = first[1].trim();
				String httpProtocol = first[2].trim();

				httpHeaderInfo.setMethod(method);
				httpHeaderInfo.setHttpProtocol(httpProtocol);

				readedFirstLine = true;
			} else {
				String[] other = line.split(":");

				if (null != other || other.length != 2) {
					continue;
				}

				String key = other[0].trim();
				String value = other[1].trim();

				map.put(key, value);
			}
		}

		if (!url.startsWith("http://")) {
			String host = map.get("Host");
			url = "http://" + host + url;
		}
		httpHeaderInfo.setHost(url);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			BasicHeader header = new BasicHeader(key, value);
			httpHeaderInfo.getHeaders().add(header);
		}

		return httpHeaderInfo;
	}
}
