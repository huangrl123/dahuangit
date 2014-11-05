package com.dahuangit.iots.perception.dto.oxm.request;

public class RemoteCtrlMachineRequest {
	private String machineAddr = null;

	private Integer opt = null;

	public String getMachineAddr() {
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

}
