package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

public class YongshuiRecordRequest extends Request {
	/** 项目id */
	private String projectId = null;

	/** 楼栋id */
	private String ldId = null;

	/** 系统id */
	private String systemId = null;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

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
