package com.dahuangit.water.proxy.dto.response;

/**
 * 收支信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:09:57
 */
public class ShouzhiInfo {
	/** 项目名称 */
	private String projectName = null;

	/** 操作员名字 */
	private String operatorName = null;

	/** 收入合计 */
	private Float sumMoney = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Float getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Float sumMoney) {
		this.sumMoney = sumMoney;
	}

}
