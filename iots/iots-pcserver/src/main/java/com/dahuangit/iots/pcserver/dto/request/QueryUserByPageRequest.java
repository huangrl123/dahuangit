package com.dahuangit.iots.pcserver.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

public class QueryUserByPageRequest extends OpRequest {
	private String userName = null;

	private Boolean isOnline = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

}
