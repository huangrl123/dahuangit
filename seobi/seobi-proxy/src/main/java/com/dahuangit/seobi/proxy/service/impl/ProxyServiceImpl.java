package com.dahuangit.seobi.proxy.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.dahuangit.util.BooleanUtils2;
import com.dahuangit.util.date.DateUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HostInfo;
import com.dahuangit.util.net.http.HttpHeaderInfo;
import com.dahuangit.util.net.http.HttpKit;

@Component
@Transactional
public class ProxyServiceImpl implements ProxyService {

	private static final Logger log = Log4jUtils.getLogger(ProxyServiceImpl.class);

	@Autowired
	private ProxyDao proxyDao = null;

	@Autowired
	private ProxyJobService proxyJobService = null;

	@Value("${proxy.local.ip}")
	private String PROXY_LOCAL_IP = null;

	@Value("${proxy.local.port}")
	private int PROXY_LOCAL_PORT;

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
			} catch (NumberFormatException e) {
				log.info("已跳过端口不合法的代理信息行:" + line);
				continue;
			}

			// 通过ip和端口进行查询，如果不存在才新增
			Proxy p = this.proxyDao.findByIpAndPort(proxyIp, proxyPort);
			if (null != p) {
				continue;
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
				proxyJobService.checkAndSetProxyStatus();
			}

		}.start();
	}

	/**
	 * 通过代理进行http请求<br>
	 * 
	 * @return
	 * @throws IOException
	 */
	public String doRequestByProxy(HttpHeaderInfo headerInfo) throws IOException {
		List<Proxy> proxyList = this.proxyDao.getAvailateProxyOrderByLastTestTime(headerInfo.getMethod());

		Proxy proxy = null;
		boolean needUpdate = true;

		// 如果没有，则使用本机进行代理
		if (null == proxyList || proxyList.isEmpty()) {
			proxy = new Proxy();
			proxy.setProxyIp(PROXY_LOCAL_IP);
			proxy.setProxyPort(PROXY_LOCAL_PORT);
			needUpdate = false;
		} else {
			for (Proxy p : proxyList) {
				// 请求之前再检查一次，如果不可用，则用下一个代理服务器
				Proxy py = proxyJobService.testProxy(p);

				boolean isAvailable = false;
				if ("GET".equalsIgnoreCase(headerInfo.getMethod())) {
					isAvailable = py.getIsHttpGetAvailable();
				} else if ("POST".equalsIgnoreCase(headerInfo.getMethod())) {
					isAvailable = py.getIsHttpPostAvailable();
				}

				if (isAvailable) {
					proxy = py;
					break;
				}
			}
		}

		String url = headerInfo.getHost();
		String method = headerInfo.getMethod();

		log.info("正在通过代理进行http请求...");
		log.info("地址=" + url);
		log.info("代理IP=" + proxy.getProxyIp());
		log.info("代理端口=" + proxy.getProxyPort());

		HostInfo proxyHost = new HostInfo();
		proxyHost.setAddr(proxy.getProxyIp());
		proxyHost.setPort(proxy.getProxyPort());
		proxyHost.setEncode(headerInfo.getEncode());

		String content = null;

		if ("GET".equalsIgnoreCase(method)) {
			try {
				content = HttpKit.doGetByProxy(url, proxyHost, headerInfo.getHeaders());
			} catch (Exception e) {
				content = e.getMessage();
				e.printStackTrace();
			}

		} else if ("POST".equalsIgnoreCase(method)) {
			try {
				content = HttpKit.doPostByProxy(url, proxyHost, null, headerInfo.getHeaders());
			} catch (Exception e) {
				content = e.getMessage();
				e.printStackTrace();
			}
		} else {
			throw new GenericException("非法的http方法：" + method);
		}

		log.info("返回内容=" + content);

		// 更新代理的最后通信时间
		if (needUpdate) {
			proxy.setLastTestTime(new Date());
			this.proxyDao.update(proxy);
		}

		StringBuffer sb = new StringBuffer();

		// /////////http响应报文所必须的参数 start////////////////////////////
		sb.append("HTTP/1.1 200 OK");
		sb.append("\r");
		sb.append("\n");

//		sb.append("Content-Type: text/html;charset=" + headerInfo.getEncode());
		sb.append("Content-Type: text/html;charset=gb2312");
		sb.append("\r");
		sb.append("\n");

		if (null == content) {
			sb.append("Content-Length: " + 0);
		} else {
			sb.append("Content-Length: " + content.getBytes().length);
		}
		sb.append("\r");
		sb.append("\n");

		sb.append("\r");
		sb.append("\n");
		// /////////http响应报文所必须的参数 end/////////////////////////////

		sb.append(content);

		return sb.toString();
	}

	public void deleteProxy(Integer id) {
		Proxy proxy = new Proxy();
		proxy.setPid(id);
		this.proxyDao.delete(proxy);

	}

	@Override
	public PageQueryResult<ProxyResponse> findByPage(Integer start, Integer limit) {
		PageQueryResult<ProxyResponse> pageQueryResult = new PageQueryResult<ProxyResponse>();

		List<Proxy> rows = this.proxyDao.findProxyByPage(start, limit);

		List<ProxyResponse> results = new ArrayList<ProxyResponse>();

		for (Proxy proxy : rows) {
			ProxyResponse pr = new ProxyResponse();
			pr.setPid(proxy.getPid());

			pr.setIsTelnetAvailable(BooleanUtils2.toStringShiFou(proxy.getIsTelnetAvailable()));
			pr.setIsHttpGetAvailable(BooleanUtils2.toStringShiFou(proxy.getIsHttpGetAvailable()));
			pr.setIsHttpPostAvailable(BooleanUtils2.toStringShiFou(proxy.getIsHttpPostAvailable()));

			pr.setLastTestTime(DateUtils.format(proxy.getLastTestTime()));

			pr.setProxyIp(proxy.getProxyIp());
			pr.setProxyPort(proxy.getProxyPort());
			pr.setRemark(proxy.getRemark());

			results.add(pr);
		}

		Long totalCount = this.proxyDao.findProxyCount();
		pageQueryResult.setTotalCount(totalCount);

		pageQueryResult.setResults(results);

		return pageQueryResult;
	}
}
