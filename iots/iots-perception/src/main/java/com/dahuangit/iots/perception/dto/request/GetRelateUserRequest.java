package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

public class GetRelateUserRequest extends Request {
	private Integer perceptionId = null;

	private String userIds = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

}
