package com.dahuangit.iots.manager.dto.request;

import com.dahuangit.base.dto.Request;

public class UserLogoutRequest extends Request {
	private Integer userId = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
