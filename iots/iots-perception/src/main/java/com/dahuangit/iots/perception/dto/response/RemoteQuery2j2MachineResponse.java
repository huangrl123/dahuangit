package com.dahuangit.iots.perception.dto.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class RemoteQuery2j2MachineResponse extends OxResponse {
	private Integer perceptionId = null;

	private String rotateStatus = null;

	private String switchStatus = null;

	private String i2cStatus = null;

	private String rotateStatus2 = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(String rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

	public String getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getI2cStatus() {
		return i2cStatus;
	}

	public void setI2cStatus(String i2cStatus) {
		this.i2cStatus = i2cStatus;
	}

	public String getRotateStatus2() {
		return rotateStatus2;
	}

	public void setRotateStatus2(String rotateStatus2) {
		this.rotateStatus2 = rotateStatus2;
	}

}
