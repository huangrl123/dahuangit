package com.dahuangit.seobi.proxy.service;

import java.io.IOException;
import java.net.SocketException;

import com.dahuangit.seobi.proxy.entry.Proxy;

public interface ProxyJobService {

	void checkAndSetProxyStatus();

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
	Proxy testProxy(Proxy proxy) throws SocketException, IOException;
}
