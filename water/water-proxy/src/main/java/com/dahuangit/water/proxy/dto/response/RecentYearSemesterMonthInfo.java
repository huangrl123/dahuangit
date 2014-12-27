package com.dahuangit.water.proxy.dto.response;

/**
 * 最近三年月份收益, 当请求参数中请求动作类型为3时返回
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:39:22
 */
public class RecentYearSemesterMonthInfo {
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
