package com.dahuangit.seobi.proxy.util;

import java.io.IOException;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.TelnetUtils;

public class ProxyUtils {

	protected static final Logger log = Log4jUtils.getLogger(ProxyUtils.class);

	/**
	 * 测试代理服务器是否可用
	 * 
	 * @param hostname
	 * @param port
	 * @param connectTimeout
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public static boolean isAvaliableServer(String hostname, int port, int connectTimeout) throws SocketException,
			IOException {
		log.info("正在 测试代理服务器是否可用，hostname=" + hostname + " port=" + port + " connectTimeout=" + connectTimeout);

		boolean isAvailable = TelnetUtils.isAvailableServer(hostname, port, connectTimeout);

		log.info("测试结果:" + (isAvailable == true ? "可用" : "不可用"));

		return isAvailable;
	}

	public static boolean isAvailableProxy(Proxy proxy) throws SocketException, IOException {
		return isAvaliableServer(proxy.getProxyIp(), proxy.getProxyPort(), 10 * 1000);
	}
}
