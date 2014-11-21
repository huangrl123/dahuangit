package com.dahuangit.iots.perception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.perception.service.RemoteCtrlService;
import com.dahuangit.iots.perception.tcpserver.dto.StatusParam;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.coder.ByteUtils;

@Component
@Transactional
public class RemoteCtrlServiceImpl implements RemoteCtrlService {

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	public PerceptionFrame doRemoteCtrl(String machineAddr, Integer opt, StatusParam statusParam) {

		byte[] arr = ByteUtils.intToByteArray(opt);
		byte bopt = arr[arr.length - 1];

		PerceptionFrame frame = perceptionProcessor.remoteOperateMachine(machineAddr, bopt, statusParam);

		return frame;
	}
}
