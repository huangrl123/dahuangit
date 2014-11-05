package com.dahuangit.iots.perception.tcpserver.frame;

/**
 * 帧对象
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionFrame {
	// //////////////////////帧头//////////////////////////
	/** 帧序列号 0xA1 LONG */
	private long seq = 0l;

	/** 帧总长度 0xA2 INT */
	private int length = 0;

	/** 业务类型 0xA3 BYTE */
	private byte busType = (byte) 0x00;

	/** CRC32校验和 0xA4 LONG */
	private long crc32 = 0;

	// //////////////////////帧体//////////////////////////
	/** 电机地址 0xB1 VARCHAR */
	private String machineAddr = null;

	/** 操作标识 0xB2 BYTE */
	private byte operateFlag = (byte) 0x00;

	/** 旋转状态 0xB3 BYTE */
	private byte rotateStatus = (byte) 0x00;

	/** 开关状态 0xB4 BYTE */
	private byte switchStatus = (byte) 0x00;

	/** 处理结果 */
	private byte result = (byte) 0x00;

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getLength() {
		return length;
	}

	public byte getBusType() {
		return busType;
	}

	public void setBusType(byte busType) {
		this.busType = busType;
	}

	public long getCrc32() {
		return crc32;
	}

	public void setCrc32(long crc32) {
		this.crc32 = crc32;
	}

	public String getMachineAddr() {
		if (null != this.machineAddr) {
			this.machineAddr = this.machineAddr.trim();
		}
		
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

	public byte getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(byte operateFlag) {
		this.operateFlag = operateFlag;
	}

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

	public void setLength(int length) {
		this.length = length;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

}
