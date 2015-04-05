package com.dahuangit.water.proxy.dto.response;

/**
 * 学期月份收益，当请求参数中请求动作类型为2时返回
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:33:34
 */
public class SemesterMonthInfo {
	/** 项目名称 */
	private String projectName = null;

	/** 月份 */
	private String month = null;

	/** 收益 */
	private Float sumSy = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Float getSumSy() {
		return sumSy;
	}

	public void setSumSy(Float sumSy) {
		this.sumSy = sumSy;
	}

}
