package com.dahuangit.seobi.proxy.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuangit.seobi.proxy.dao.ProxyDao;
import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.seobi.proxy.util.ProxyUtils;
import com.dahuangit.util.log.Log4jUtils;

@Component
public class ProxyJobServiceImpl {

	private static final Logger log = Log4jUtils.getLogger(ProxyJobServiceImpl.class);

	@Autowired
	private ProxyDao proxyDao = null;

	public void CheckAndSetProxyStatus() {
		log.debug("检查和设置代理服务器状态定时任务启动...");

		// 可能会有点多
		List<Proxy> proxies = this.proxyDao.getAll();

		for (Proxy proxy : proxies) {
			try {
				boolean isAvailable = ProxyUtils.isAvailableProxy(proxy);

				if (isAvailable) {
					proxy.setAvailable(true);
				} else {
					proxy.setAvailable(false);
				}

				proxy.setLastTestTime(new Date());

				this.proxyDao.update(proxy);
			} catch (Exception e) {
				log.error("定时任务检查和设置代理服务器状态是发生了错误，系统跳过此条数据，继续检查" + e.getMessage());
			}
		}

		log.debug("检查和设置代理服务器状态定时任务执行完毕,等待下次任务执行!");
	}

}
