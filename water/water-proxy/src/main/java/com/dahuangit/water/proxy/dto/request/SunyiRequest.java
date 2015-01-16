package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 损益请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:26:07
 */
public class SunyiRequest extends Request {
	/** 项目id */
	private String projectId = null;

	/** 项目名称 */
	private String projectName = null;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
