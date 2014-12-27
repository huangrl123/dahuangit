package com.dahuangit.water.proxy.dto.response;

/**
 * 监控信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午5:39:47
 */
public class JiankongInfo {

	/** 项目名称 */
	private String projectName = null;

	/** 热泵机号 */
	private String deviceAddr = null;

	/** 用电量 */
	private String sumYd = null;

	/** 用水量 */
	private String sumYs = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDeviceAddr() {
		return deviceAddr;
	}

	public void setDeviceAddr(String deviceAddr) {
		this.deviceAddr = deviceAddr;
	}

	public String getSumYd() {
		return sumYd;
	}

	public void setSumYd(String sumYd) {
		this.sumYd = sumYd;
	}

	public String getSumYs() {
		return sumYs;
	}

	public void setSumYs(String sumYs) {
		this.sumYs = sumYs;
	}

}
