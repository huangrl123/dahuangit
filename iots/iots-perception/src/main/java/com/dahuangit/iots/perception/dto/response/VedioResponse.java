package com.dahuangit.iots.perception.dto.response;

import com.dahuangit.base.dto.Response;

public class VedioResponse extends Response {
	private String fileName = null;

	private String url = null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
