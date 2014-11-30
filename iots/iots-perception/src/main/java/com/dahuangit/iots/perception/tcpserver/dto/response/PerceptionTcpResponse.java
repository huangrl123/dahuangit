package com.dahuangit.iots.perception.tcpserver.dto.response;

import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;

public class PerceptionTcpResponse extends PerceptionTcpDto {
	/** 处理结果 */
	private byte result = 1;

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

}
