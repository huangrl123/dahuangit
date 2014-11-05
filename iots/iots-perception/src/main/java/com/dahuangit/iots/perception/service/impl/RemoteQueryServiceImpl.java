package com.dahuangit.iots.perception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuangit.iots.perception.dto.oxm.response.RemoteQueryMachineResponse;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.coder.ByteUtils;

@Component
public class RemoteQueryServiceImpl implements RemoteQueryService {

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	public RemoteQueryMachineResponse doRemoteQuery(String machineAddr) {
		PerceptionFrame frame = perceptionProcessor.queryRemoteMachine(machineAddr);

		RemoteQueryMachineResponse response = new RemoteQueryMachineResponse();

		response.setMachineAddr(machineAddr);

		if (frame.getResult() != (byte) 0x01) {
			response.setSuccess(false);
			int result = ByteUtils.byteArrToInt(new byte[] { frame.getResult() });
			response.setMsg(String.valueOf(result));
			return response;
		}

		int rotateStatus = ByteUtils.byteArrToInt(new byte[] { frame.getRotateStatus() });
		response.setRotateStatus(rotateStatus);
		
		int switchStatus = ByteUtils.byteArrToInt(new byte[] { frame.getSwitchStatus() });
		response.setSwitchStatus(switchStatus);

		return response;
	}
}
