package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 预警请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:17:19
 */
public class YujingRequest extends Request {

	/** 开始时间 */
	private String beginTime = null;

	/** 结束时间 */
	private String endTime = null;

	/** 项目id */
	private String projectId = null;

	/** 系统id */
	private String systemId = null;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

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
