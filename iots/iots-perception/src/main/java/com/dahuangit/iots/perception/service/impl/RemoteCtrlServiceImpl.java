package com.dahuangit.iots.perception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuangit.iots.perception.dto.oxm.response.RemoteCtrlMachineResponse;
import com.dahuangit.iots.perception.service.RemoteCtrlService;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.coder.ByteUtils;

@Component
public class RemoteCtrlServiceImpl implements RemoteCtrlService {

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	public RemoteCtrlMachineResponse doRemoteCtrl(String machineAddr, Integer opt) {
		RemoteCtrlMachineResponse response = new RemoteCtrlMachineResponse();

		byte[] arr = ByteUtils.intToByteArray(opt);
        byte bopt = arr[arr.length - 1];
        
		PerceptionFrame frame = perceptionProcessor.remoteOperateMachine(machineAddr, bopt);

		response.setMachineAddr(machineAddr);

		if (frame.getResult() != (byte) 0x01) {
			response.setSuccess(false);
			response.setMsg(String.valueOf(ByteUtils.byteArrToInt(new byte[] { frame.getResult() })));
			return response;
		}

		return response;
	}
}
