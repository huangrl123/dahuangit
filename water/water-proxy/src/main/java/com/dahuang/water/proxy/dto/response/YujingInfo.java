package com.dahuang.water.proxy.dto.response;

/**
 * 预警信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:19:26
 */
public class YujingInfo {
	/** 项目名称 */
	private String projectName = null;

	/** 所属区域 */
	private String areaName = null;

	/** 设备地址 */
	private String deviceAddr = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDeviceAddr() {
		return deviceAddr;
	}

	public void setDeviceAddr(String deviceAddr) {
		this.deviceAddr = deviceAddr;
	}

}
