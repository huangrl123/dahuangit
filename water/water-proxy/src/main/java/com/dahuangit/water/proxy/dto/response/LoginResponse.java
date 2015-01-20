package com.dahuangit.water.proxy.dto.response;

import com.dahuangit.base.dto.Response;

public class LoginResponse extends Response {
	/** 系统id */
	private String systemId = null;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
