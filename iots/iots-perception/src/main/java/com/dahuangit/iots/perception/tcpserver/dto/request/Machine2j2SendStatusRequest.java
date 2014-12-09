package com.dahuangit.iots.perception.tcpserver.dto.request;

public class Machine2j2SendStatusRequest extends PerceptionTcpRequest {
	/** 电机1旋转状态 0xB3 BYTE */
	private byte machine1RotateStatus = (byte) 0x00;
	/** 电机2旋转状态 0xB3 BYTE */
	private byte machine2RotateStatus = (byte) 0x00;
	/** 电机1开关状态 0xB4 BYTE */
	private byte machine1SwitchStatus = (byte) 0x00;
	/** 电机2开关状态 0xB4 BYTE */
	private byte machine2SwitchStatus = (byte) 0x00;
	/** i2c状态 */
	private byte[] i2cStatus = new byte[2];
	/** 红外状态 */
	private byte infraredStatus = 0;
	/** 按键状态 */
	private byte pressKeyStatus = 0;

	public byte getMachine1RotateStatus() {
		return machine1RotateStatus;
	}

	public void setMachine1RotateStatus(byte machine1RotateStatus) {
		this.machine1RotateStatus = machine1RotateStatus;
	}

	public byte getMachine2RotateStatus() {
		return machine2RotateStatus;
	}

	public void setMachine2RotateStatus(byte machine2RotateStatus) {
		this.machine2RotateStatus = machine2RotateStatus;
	}

	public byte getMachine1SwitchStatus() {
		return machine1SwitchStatus;
	}

	public void setMachine1SwitchStatus(byte machine1SwitchStatus) {
		this.machine1SwitchStatus = machine1SwitchStatus;
	}

	public byte getMachine2SwitchStatus() {
		return machine2SwitchStatus;
	}

	public void setMachine2SwitchStatus(byte machine2SwitchStatus) {
		this.machine2SwitchStatus = machine2SwitchStatus;
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

	public byte getPressKeyStatus() {
		return pressKeyStatus;
	}

	public void setPressKeyStatus(byte pressKeyStatus) {
		this.pressKeyStatus = pressKeyStatus;
	}

}
