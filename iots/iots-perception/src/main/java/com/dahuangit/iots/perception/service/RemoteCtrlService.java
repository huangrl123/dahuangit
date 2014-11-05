package com.dahuangit.iots.perception.service;

import com.dahuangit.iots.perception.dto.oxm.response.RemoteCtrlMachineResponse;

public interface RemoteCtrlService {

	RemoteCtrlMachineResponse doRemoteCtrl(String machineAddr, Integer opt);

}
