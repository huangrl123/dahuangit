package com.dahuangit.iots.perception.tcpserver.dto;

import java.util.Date;

public class PerceptionTcpDto {
	/** 帧序列号 0xA1 LONG */
	private long seq = 0l;

	/** 帧总长度 0xA2 INT */
	private int length = 0;

	/** 业务类型 0xA3 BYTE */
	private byte busType = (byte) 0x00;

	/** CRC32校验和 0xA4 LONG */
	private long crc32 = 0;

	/** 电机地址 0xB1 VARCHAR */
	private String machineAddr = null;

	/** 操作标识 0xB2 BYTE */
	private byte operateFlag = (byte) 0x00;

	/** 感知端设备类型 */
	private byte perceptionType = 0;

	/** 操作时间 */
	private Date optTime = new Date();

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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

	public byte getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(byte perceptionType) {
		this.perceptionType = perceptionType;
	}

	public Date getOptTime() {
		return optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

}
