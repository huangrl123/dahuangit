package com.dahuangit.iots.perception.dto.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class RemoteQuery2j2MachineResponse extends OxResponse {
	private Integer perceptionId = null;

	/** 电机1旋转状态 */
	private String machine1RotateStatus = null;
	/** 电机2旋转状态 */
	private String machine2RotateStatus = null;
	/** i2c状态 */
	private String i2cStatus = null;
	/** 红外状态 */
	private String infraredStatus = null;
	/** 按键状态 */
	private String pressKeyStatus = null;
	/** 记录的创建时间 */
	private String createDatetime = null;
	/** 在线状态 */
	private String onlineStatus = null;

	private String hex = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getMachine1RotateStatus() {
		return machine1RotateStatus;
	}

	public void setMachine1RotateStatus(String machine1RotateStatus) {
		this.machine1RotateStatus = machine1RotateStatus;
	}

	public String getMachine2RotateStatus() {
		return machine2RotateStatus;
	}

	public void setMachine2RotateStatus(String machine2RotateStatus) {
		this.machine2RotateStatus = machine2RotateStatus;
	}

	public String getI2cStatus() {
		return i2cStatus;
	}

	public void setI2cStatus(String i2cStatus) {
		this.i2cStatus = i2cStatus;
	}

	public String getInfraredStatus() {
		return infraredStatus;
	}

	public void setInfraredStatus(String infraredStatus) {
		this.infraredStatus = infraredStatus;
	}

	public String getPressKeyStatus() {
		return pressKeyStatus;
	}

	public void setPressKeyStatus(String pressKeyStatus) {
		this.pressKeyStatus = pressKeyStatus;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

}
