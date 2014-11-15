package com.dahuangit.seobi.analyzer.util;

import java.io.IOException;
import java.net.URLEncoder;

import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.util.net.http.HostInfo;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.net.http.ProxyHttpResponse;
import com.dahuangit.util.string.StringUtils2;

public class SearchUtils {

	private static final String BAIDU_SEARCH_BASE_URL = "http://www.baidu.com/s?wd=%s&rn=20&lm=0";

	public static String searchByKey(Proxy proxy, String key) throws IOException {
		key = URLEncoder.encode(key);
		String result = null;
		String url = StringUtils2.StringFormat(BAIDU_SEARCH_BASE_URL, key);

		try {
			ProxyHttpResponse response = null;
			if (null != proxy) {
				HostInfo proxyHost = new HostInfo();
				proxyHost.setAddr(proxy.getProxyIp());
				proxyHost.setPort(proxy.getProxyPort());
				response = HttpKit.doGetByProxy(url, proxyHost, null);
			} else {
				response = HttpKit.doGetByProxy(url, null, null);
			}

			if (null != response) {
				result = response.getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果使用代理服务器不成功，则不使用代理服务器了
		if (result == null) {
			result = HttpKit.doHttpRequest(url);
		}

		return result;
	}
}
