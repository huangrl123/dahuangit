package com.dahuangit.seobi.proxy.dto.oxm.request;

import com.dahuangit.base.dto.oxm.request.OxRequest;

public class DoRequestByProxyRequest extends OxRequest {

	private String url = null;

	private String encode = null;

	private String method = null;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
