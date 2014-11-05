package com.dahuangit.iots.perception.dto.oxm.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class RemoteQueryMachineResponse extends OxResponse {
	private String machineAddr = null;

	private Integer rotateStatus = null;

	private Integer switchStatus = null;

	public String getMachineAddr() {
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

	public Integer getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(Integer rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

}
