package com.dahuangit.iots.perception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionParamValueDao;
import com.dahuangit.iots.perception.dto.oxm.response.RemoteQueryMachineResponse;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.coder.ByteUtils;

@Component
@Transactional
public class RemoteQueryServiceImpl implements RemoteQueryService {

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	@Autowired
	private PerceptionParamValueDao perceptionParamValueDao = null;

	@Autowired
	private PerceptionDao perceptionDao = null;

	public RemoteQueryMachineResponse doRemoteQuery(Integer perceptionId) {
		PerceptionFrame frame = perceptionProcessor.queryRemoteMachine(perceptionId);

		RemoteQueryMachineResponse response = new RemoteQueryMachineResponse();

		if (frame.getResult() != (byte) 0x01) {
			response.setSuccess(false);
			int result = ByteUtils.byteArrToInt(new byte[] { frame.getResult() });
			response.setMsg(String.valueOf(result));
			return response;
		}

		//Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		// Integer perceptionTypeId = p.getPerceptionTypeId();

		int switchStatus = ByteUtils.byteArrToInt(new byte[] { frame.getSwitchStatus() });
		// Integer switchStatusParamValue =
		// this.perceptionParamValueDao.getPerceptionParamValueInfoId(perceptionTypeId,
		// 1, Integer.valueOf(switchStatus));
		

		int rotateStatus = ByteUtils.byteArrToInt(new byte[] { frame.getRotateStatus() });
		// Integer rotateStatusParamValue =
		// this.perceptionParamValueDao.getPerceptionParamValueInfoId(perceptionTypeId,
		// 2, Integer.valueOf(rotateStatus));
		
		response.setSwitchStatus(String.valueOf(switchStatus));
		response.setRotateStatus(String.valueOf(rotateStatus));

		return response;
	}
}
