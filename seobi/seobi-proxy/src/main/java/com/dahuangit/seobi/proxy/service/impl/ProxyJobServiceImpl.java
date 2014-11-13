package com.dahuangit.seobi.proxy.service.impl;

import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.seobi.proxy.dao.ProxyDao;
import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.seobi.proxy.service.ProxyJobService;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.TelnetUtils;
import com.dahuangit.util.net.http.HostInfo;
import com.dahuangit.util.net.http.HttpKit;

@Transactional
public class ProxyJobServiceImpl implements ProxyJobService {

	private static final Logger log = Log4jUtils.getLogger(ProxyJobServiceImpl.class);

	@Autowired
	private ProxyDao proxyDao = null;

	private String httpGetTestUrl = null;

	private String httpPostTestUrl = null;

	public void checkAndSetProxyStatus() {
		log.debug("检查和设置代理服务器状态定时任务启动...");

		// 可能会有点多
		List<Proxy> proxies = this.proxyDao.getAll();

		for (int i = 0; i < proxies.size(); i++) {
			Proxy proxy = proxies.get(i);
			try {
				log.debug("正在测试的代理服务器" + (i + 1) + "/" + proxies.size() + "....................................");
				this.testProxy(proxy);
				log.debug("代理服务器" + (i + 1) + "/" + proxies.size() + "测试完毕");
			} catch (Exception e) {
				log.error("定时任务检查和设置代理服务器状态是发生了错误，系统跳过此条数据，继续检查" + e.getMessage());
			}
		}

		log.debug("检查和设置代理服务器状态定时任务执行完毕,等待下次任务执行!");
	}

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
	public Proxy testProxy(Proxy proxy) throws SocketException, IOException {
		String hostname = proxy.getProxyIp();
		Integer port = proxy.getProxyPort();

		Validate.notNull(hostname, "proxy.getProxyIp()不能为null");
		Validate.notNull(port, "proxy.getProxyPort()不能为null");

		log.debug("代理服务器测试开始,ip=[" + hostname + "] port=[" + port + "]...");
		// telnet测试
		boolean isTelnetAvailable = false;
		try {
			isTelnetAvailable = TelnetUtils.isAvailableServer(hostname, port, 5 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!isTelnetAvailable) {
			log.debug("telnet不可用，系统将不再该代理服务器的其他命令...");
			proxy.setLastTestTime(new Date());
			return proxy;
		}

		log.debug("代理服务器ip=[" + hostname + "] port=[" + port + "] telnet测试完毕，测试通过！");

		// http get测试
		HostInfo hostInfo = new HostInfo();
		hostInfo.setAddr(hostname);
		hostInfo.setPort(port);

		boolean isHttpGetAvailable = HttpKit.isProxyServerHttpGetAvailable(hostInfo, this.httpGetTestUrl);
		if (isHttpGetAvailable) {
			log.debug("代理服务器ip=[" + hostname + "] port=[" + port + "] http get测试完毕，测试通过！");
		} else {
			log.debug("代理服务器ip=[" + hostname + "] port=[" + port + "] http get测试完毕，测试不通过！！！！");
		}

		// http post测试
		boolean isHttpPostAvailable = HttpKit.isProxyServerHttpPostAvailable(hostInfo, httpPostTestUrl);
		if (isHttpPostAvailable) {
			log.debug("代理服务器ip=[" + hostname + "] port=[" + port + "] http post测试完毕，测试通过！");
		} else {
			log.debug("代理服务器ip=[" + hostname + "] port=[" + port + "] http post测试完毕，测试不通过！！！！");
		}

		proxy.setIsTelnetAvailable(true);
		proxy.setIsHttpGetAvailable(isHttpGetAvailable);
		proxy.setIsHttpPostAvailable(isHttpPostAvailable);
		proxy.setLastTestTime(new Date());

		return proxy;
	}

	public String getHttpGetTestUrl() {
		return httpGetTestUrl;
	}

	public void setHttpGetTestUrl(String httpGetTestUrl) {
		this.httpGetTestUrl = httpGetTestUrl;
	}

	public String getHttpPostTestUrl() {
		return httpPostTestUrl;
	}

	public void setHttpPostTestUrl(String httpPostTestUrl) {
		this.httpPostTestUrl = httpPostTestUrl;
	}

}
