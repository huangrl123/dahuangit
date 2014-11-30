package com.dahuangit.iots.perception.tcpserver.dto.response;

public class ServerQueryMachine2j2StatusResponse extends PerceptionTcpResponse {
	/** 旋转状态 0xB3 BYTE */
	private byte rotateStatus = (byte) 0x00;

	/** 开关状态 0xB4 BYTE */
	private byte switchStatus = (byte) 0x00;

	/** i2c状态 */
	private byte[] i2cStatus = new byte[2];

	/** 旋转状态2 */
	private byte rotateStatus2 = 0;

	public byte getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(byte rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

	public byte getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(byte switchStatus) {
		this.switchStatus = switchStatus;
	}

	public byte[] getI2cStatus() {
		return i2cStatus;
	}

	public void setI2cStatus(byte[] i2cStatus) {
		this.i2cStatus = i2cStatus;
	}

	public byte getRotateStatus2() {
		return rotateStatus2;
	}

	public void setRotateStatus2(byte rotateStatus2) {
		this.rotateStatus2 = rotateStatus2;
	}

}
