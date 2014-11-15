package com.dahuangit.seobi.proxy.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.proxy.dto.opm.response.ProxyResponse;
import com.dahuangit.seobi.proxy.entry.Proxy;
import com.dahuangit.util.net.http.HttpHeaderInfo;

public interface ProxyService {

	void addProxy(String ip, Integer port);

	void deleteProxy(Integer id);

	String doRequestByProxy(HttpHeaderInfo headerInfo) throws IOException;

	void impProxy(MultipartFile multipartFile) throws IOException;

	PageQueryResult<ProxyResponse> findByPage(Integer start, Integer limit);

	/**
	 * 获取最优代理服务器
	 * 
	 * @return
	 */
	Proxy getOptimalProxy(String method);
}
