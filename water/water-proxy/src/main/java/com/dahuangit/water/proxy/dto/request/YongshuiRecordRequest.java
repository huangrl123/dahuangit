package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

public class YongshuiRecordRequest extends Request {
	/** 项目id */
	private String projectId = null;

	/** 楼栋id */
	private String ldId = null;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLdId() {
		return ldId;
	}

	public void setLdId(String ldId) {
		this.ldId = ldId;
	}

}
