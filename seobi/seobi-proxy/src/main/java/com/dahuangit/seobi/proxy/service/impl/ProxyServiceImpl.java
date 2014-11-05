package com.dahuangit.seobi.proxy.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.base.exception.GenericException;
import com.dahuangit.seobi.proxy.dao.ProxyDao;
import com.dahuangit.seobi.proxy.dto.opm.response.ProxyResponse;
import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.seobi.proxy.service.ProxyJobService;
import com.dahuangit.seobi.proxy.service.ProxyService;
import com.dahuangit.util.bean.dto.DtoBuilder;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.HttpKit;

@Component
@Transactional
public class ProxyServiceImpl implements ProxyService {

	private static final Logger log = Log4jUtils.getLogger(ProxyServiceImpl.class);

	@Autowired
	private ProxyDao proxyDao = null;

	@Autowired
	private ProxyJobService proxyJobService = null;

	/**
	 * 批量导入代理服务器信息
	 * 
	 * @param multipartFile
	 * @throws IOException
	 */
	public void impProxy(MultipartFile multipartFile) throws IOException {
		byte[] btArr = multipartFile.getBytes();

		ByteArrayInputStream bis = new ByteArrayInputStream(btArr);
		InputStreamReader isr = new InputStreamReader(bis);

		BufferedReader br = new BufferedReader(isr);

		String line = null;
		List<Proxy> proxies = new ArrayList<Proxy>();

		while (null != (line = br.readLine())) {
			String[] proxyInfo = line.split(":");
			if (proxyInfo.length != 2) {
				log.info("已跳过到不符合格式的代理信息行:" + line);
				continue;
			}

			String proxyIp = proxyInfo[0].trim();
			int proxyPort = 0;
			try {
				proxyPort = Integer.parseInt(proxyInfo[1].trim());
				continue;
			} catch (NumberFormatException e) {
				log.info("已跳过端口不合法的代理信息行:" + line);
			}

			Proxy proxy = new Proxy();
			proxy.setProxyIp(proxyIp);
			proxy.setProxyPort(proxyPort);

			proxies.add(proxy);
		}

		this.proxyDao.batchAdd(proxies);

		// 启动线程开始检查这些服务器是否可用
		new Thread() {

			@Override
			public void run() {
				proxyJobService.CheckAndSetProxyStatus();
			}

		}.start();
	}

	/**
	 * 通过代理进行http请求<br>
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String doRequestByProxy(String url, String encode, String method) throws IOException {
		Proxy proxy = this.proxyDao.getAarliestLastTestTimeAvailateProxy();

		if (null == proxy) {
			throw new GenericException("无法找到可用的代理服务器，请确认当前是否有代理服务器列表是否为空");
		}

		log.info("正在通过代理进行http请求...");
		log.info("地址=" + url);
		log.info("编码=" + encode);
		log.info("代理IP=" + proxy.getProxyIp());
		log.info("代理端口=" + proxy.getProxyPort());

		String content = null;

		if ("GET".equalsIgnoreCase(method)) {
			content = HttpKit.doGetByProxy(url, encode, proxy.getProxyIp(), proxy.getProxyPort());
		} else if ("POST".equalsIgnoreCase(method)) {
			Map<String, String> params = new HashMap<String, String>();

			content = HttpKit.doPostByProxy(url, params, encode, proxy.getProxyIp(), proxy.getProxyPort());
		} else {
			throw new GenericException("非法的http方法：" + method);
		}

		log.info("返回内容=" + content);

		return content;
	}

	public void deleteProxy(Integer id) {
		Proxy proxy = new Proxy();
		proxy.setPid(id);
		this.proxyDao.delete(proxy);

	}

	@Override
	public PageQueryResult<ProxyResponse> findByPage(Integer start, Integer limit) {
		String listHql = "from Proxy p order by p.lastTestTime desc";
		String countHql = "select count(*)  from Proxy";

		PageQueryResult<ProxyResponse> pageQueryResult = new PageQueryResult<ProxyResponse>();

		List<Proxy> rows = this.proxyDao.findByPage(listHql, start, limit);

		PageQueryResult<ProxyResponse> queryResult = new PageQueryResult<ProxyResponse>();
		for (Proxy proxy : rows) {
			queryResult.getResults().add(DtoBuilder.buildDto(ProxyResponse.class, new Object[] { proxy }));
		}

		Integer totalCount = this.proxyDao.findRecordsCount(countHql);
		pageQueryResult.setTotalCount(totalCount);

		return pageQueryResult;
	}
}
