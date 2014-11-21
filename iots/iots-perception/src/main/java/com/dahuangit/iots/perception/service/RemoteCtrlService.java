package com.dahuangit.iots.perception.service;

import com.dahuangit.iots.perception.tcpserver.dto.StatusParam;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;

public interface RemoteCtrlService {

	public PerceptionFrame doRemoteCtrl(String machineAddr, Integer opt, StatusParam statusParam);
	
}
