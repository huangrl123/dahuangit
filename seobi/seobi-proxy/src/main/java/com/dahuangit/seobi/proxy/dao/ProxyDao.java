package com.dahuangit.seobi.proxy.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.seobi.proxy.entry.Proxy;

@Component
public class ProxyDao extends BaseDao<Proxy, Long> {

	/**
	 * 获取上次测试时间最早的可用代理服务器
	 * 
	 * @return
	 */
	public Proxy getAarliestLastTestTimeAvailateProxy() {
		String hql = "from Proxy p where p.available=? order by p.lastTestTime asc";
		List<Proxy> proxyList = this.find(hql, true);

		if (null != proxyList && proxyList.isEmpty()) {
			return proxyList.get(0);
		}

		return null;
	}

	public List<Proxy> findProxyByPage(Integer start, Integer limit) {
		String hql = "from Proxy";
		return this.findByPage(hql, start, limit);
	}

	public Integer findProxyCount() {
		String hql = "from Proxy";
		return this.findRecordsCount(hql);
	}
}
