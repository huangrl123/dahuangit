package com.dahuangit.iots.perception.tcpserver.dto.response;

public class ServerQueryMachine6j6StatusResponse extends PerceptionTcpResponse {
	/** 旋转状态 0xB3 BYTE */
	private byte rotateStatus = (byte) 0x00;

	/** 开关状态 0xB4 BYTE */
	private byte switchStatus = (byte) 0x00;

	/** i2c状态 */
	private byte[] i2cStatus = new byte[2];

	/** 红外状态 */
	private byte infraredStatus = 0;

	/** 振动状态 */
	private byte vibrateStatus = 0;

	/** 压力状态 */
	private byte pressureStatus = 0;

	/** 接近状态 */
	private byte approachStatus = 0;

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

	public byte getInfraredStatus() {
		return infraredStatus;
	}

	public void setInfraredStatus(byte infraredStatus) {
		this.infraredStatus = infraredStatus;
	}

	public byte getVibrateStatus() {
		return vibrateStatus;
	}

	public void setVibrateStatus(byte vibrateStatus) {
		this.vibrateStatus = vibrateStatus;
	}

	public byte getPressureStatus() {
		return pressureStatus;
	}

	public void setPressureStatus(byte pressureStatus) {
		this.pressureStatus = pressureStatus;
	}

	public byte getApproachStatus() {
		return approachStatus;
	}

	public void setApproachStatus(byte approachStatus) {
		this.approachStatus = approachStatus;
	}

	public byte getRotateStatus2() {
		return rotateStatus2;
	}

	public void setRotateStatus2(byte rotateStatus2) {
		this.rotateStatus2 = rotateStatus2;
	}

}
