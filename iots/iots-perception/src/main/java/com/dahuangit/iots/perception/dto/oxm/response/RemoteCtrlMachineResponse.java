package com.dahuangit.iots.perception.dto.oxm.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class RemoteCtrlMachineResponse extends OxResponse {

	private String machineAddr = null;

	public String getMachineAddr() {
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

}
