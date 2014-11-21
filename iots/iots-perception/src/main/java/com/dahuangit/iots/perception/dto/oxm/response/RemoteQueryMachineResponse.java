package com.dahuangit.iots.perception.dto.oxm.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class RemoteQueryMachineResponse extends OxResponse {
	private Integer perceptionId = null;

	private String rotateStatus = null;

	private String switchStatus = null;

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

}
