package com.dahuangit.seobi.proxy.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.proxy.dto.opm.response.ProxyResponse;
import com.dahuangit.util.net.http.HttpHeaderInfo;

public interface ProxyService {

	void deleteProxy(Integer id);

	String doRequestByProxy(String url, String encode, String method) throws IOException;

	public String doRequestByProxy(HttpHeaderInfo headerInfo) throws IOException;
	
	void impProxy(MultipartFile multipartFile) throws IOException;

	PageQueryResult<ProxyResponse> findByPage(Integer start, Integer limit);
}
