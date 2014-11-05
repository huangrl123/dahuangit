package com.dahuangit.iots.perception.service;

import com.dahuangit.iots.perception.dto.oxm.response.RemoteQueryMachineResponse;

public interface RemoteQueryService {
	
	RemoteQueryMachineResponse doRemoteQuery(String machineAddr);
}
