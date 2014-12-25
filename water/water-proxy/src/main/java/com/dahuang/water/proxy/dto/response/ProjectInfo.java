package com.dahuang.water.proxy.dto.response;

/**
 * 项目信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午3:56:28
 */
public class ProjectInfo {
	/** 项目id当该节点值为0时，项目名称为所有项目 */
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
