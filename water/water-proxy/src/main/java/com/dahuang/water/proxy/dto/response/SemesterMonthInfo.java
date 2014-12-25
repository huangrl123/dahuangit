package com.dahuang.water.proxy.dto.response;

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
	private String yMonth = null;

	/** 收益 */
	private String sumSy = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getyMonth() {
		return yMonth;
	}

	public void setyMonth(String yMonth) {
		this.yMonth = yMonth;
	}

	public String getSumSy() {
		return sumSy;
	}

	public void setSumSy(String sumSy) {
		this.sumSy = sumSy;
	}

}
