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
	public List<Proxy> getAvailateProxyOrderByLastTestTime(String httpMethodName) {
		String hql = null;

		if ("GET".equalsIgnoreCase(httpMethodName)) {
			hql = "from Proxy p where p.isHttpGetAvailable=? order by p.lastTestTime asc";
		} else if ("POST".equalsIgnoreCase(httpMethodName)) {
			hql = "from Proxy p where p.isHttpPostAvailable=? order by p.lastTestTime asc";
		} else {
			throw new RuntimeException("不支持的http方法:[" + httpMethodName + "]");
		}

		List<Proxy> proxyList = this.find(hql, true);

		return proxyList;
	}

	public List<Proxy> findProxyByPage(Integer start, Integer limit) {
		String hql = "from Proxy p order by p.isHttpGetAvailable desc,p.isHttpPostAvailable desc,p.isTelnetAvailable desc";
		return this.findByPage(hql, start, limit);
	}

	public Long findProxyCount() {
		String hql = "select count(*) from Proxy";
		return this.findRecordsCount(hql);
	}

	public Proxy findByIpAndPort(String ip, Integer port) {
		String hql = "from Proxy p where p.proxyIp=? and p.proxyPort=?";
		return this.findUnique(hql, ip, port);
	}
}
