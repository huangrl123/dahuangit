package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 收支request
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:07:43
 */
public class ShouzhiRequest extends Request {
	/** 开始时间 */
	private String beginTime = null;

	/** 结束时间 */
	private String endTime = null;

	/** 项目id */
	private String projectId = null;

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
