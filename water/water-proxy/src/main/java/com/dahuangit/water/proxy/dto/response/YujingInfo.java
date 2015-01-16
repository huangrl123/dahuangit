package com.dahuangit.water.proxy.dto.response;

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

	/** 公寓名称 */
	private String buildName = null;

	/** 房间名称 */
	private String roomName = null;

	/** 消费金额 */
	private String sumMoney = null;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

}
