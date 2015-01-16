package com.dahuangit.water.proxy.dto.response;

/**
 * 学期概况
 * 
 * @author 大黄
 * 
 *         2015年1月15日上午10:56:16
 */
public class SemesterSumInfo {
	/** 项目名称 */
	private String projectName = null;

	/** 收支合计 */
	private String sumInout = null;

	/** 消费合计 */
	private String sumXf = null;

	/** 赠送合计 */
	private String sumZs = null;

	/** 成本合计 */
	private String sumCb = null;

	/** 项目收益 */
	private String sumSy = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSumInout() {
		return sumInout;
	}

	public void setSumInout(String sumInout) {
		this.sumInout = sumInout;
	}

	public String getSumXf() {
		return sumXf;
	}

	public void setSumXf(String sumXf) {
		this.sumXf = sumXf;
	}

	public String getSumZs() {
		return sumZs;
	}

	public void setSumZs(String sumZs) {
		this.sumZs = sumZs;
	}

	public String getSumCb() {
		return sumCb;
	}

	public void setSumCb(String sumCb) {
		this.sumCb = sumCb;
	}

	public String getSumSy() {
		return sumSy;
	}

	public void setSumSy(String sumSy) {
		this.sumSy = sumSy;
	}
}
