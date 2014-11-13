package com.dahuangit.util.net.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicHeader;

public class HttpHeaderInfo {
	private String method = null;

	private String host = null;

	private String httpProtocol = null;

	private String encode = "UTF-8";

	private List<BasicHeader> headers = new ArrayList<BasicHeader>();

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHttpProtocol() {
		return httpProtocol;
	}

	public void setHttpProtocol(String httpProtocol) {
		this.httpProtocol = httpProtocol;
	}

	public List<BasicHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<BasicHeader> headers) {
		this.headers = headers;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

}
