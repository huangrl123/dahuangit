package com.dahuangit.iots.perception.tcpserver.dto;

public class StatusParam {
	private byte switchStatus = 0x00;

	private byte rotateStatus = 0x00;

	public byte getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(byte switchStatus) {
		this.switchStatus = switchStatus;
	}

	public byte getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(byte rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

}
