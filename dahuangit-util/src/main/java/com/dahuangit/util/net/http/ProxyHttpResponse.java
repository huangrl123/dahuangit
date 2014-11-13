package com.dahuangit.util.net.http;

/**
 * 代理服务器返回的响应
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月13日 下午3:28:55
 */
public class ProxyHttpResponse {
	private String contentType = "text/html; charset=UTF-8";

	private String content = null;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
