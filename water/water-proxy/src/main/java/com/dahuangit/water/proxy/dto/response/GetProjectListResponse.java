package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 获取项目列表响应类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午3:58:22
 */
public class GetProjectListResponse extends Response {

	private List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();

	public List<ProjectInfo> getProjectInfos() {
		return projectInfos;
	}

	public void setProjectInfos(List<ProjectInfo> projectInfos) {
		this.projectInfos = projectInfos;
	}

}
