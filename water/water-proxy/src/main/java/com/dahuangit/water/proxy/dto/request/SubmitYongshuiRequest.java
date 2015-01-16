package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

public class SubmitYongshuiRequest extends Request {
	/** 项目id */
	private String projectId = null;

	/** 楼栋id */
	private String ldId = null;

	/** 用水金额 */
	private String yongshuiSum = null;

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

	public String getYongshuiSum() {
		return yongshuiSum;
	}

	public void setYongshuiSum(String yongshuiSum) {
		this.yongshuiSum = yongshuiSum;
	}

}
