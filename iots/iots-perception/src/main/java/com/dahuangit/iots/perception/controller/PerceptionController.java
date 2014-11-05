package com.dahuangit.iots.perception.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.iots.perception.dto.oxm.request.RemoteCtrlMachineRequest;
import com.dahuangit.iots.perception.dto.oxm.response.RemoteCtrlMachineResponse;
import com.dahuangit.iots.perception.dto.oxm.response.RemoteQueryMachineResponse;
import com.dahuangit.iots.perception.service.RemoteCtrlService;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.util.log.Log4jUtils;

@Controller
@RequestMapping("/perception")
public class PerceptionController extends BaseController {

	private static final Logger log = Log4jUtils.getLogger(PerceptionController.class);

	@Autowired
	private RemoteQueryService remoteQueryService = null;

	@Autowired
	private RemoteCtrlService remoteCtrlService = null;

	@RequestMapping(value = "/remoteQueryMachine", method = RequestMethod.POST)
	@ResponseBody
	public String remoteQueryMachine(String machineAddr) throws Exception {

		RemoteQueryMachineResponse response = remoteQueryService.doRemoteQuery(machineAddr);

		return this.responseToXml(response);
	}

	@RequestMapping(value = "/remoteCtrlMachine", method = RequestMethod.POST)
	@ResponseBody
	public String remoteCtrlMachine(String xml) throws Exception {

		RemoteCtrlMachineRequest request = (RemoteCtrlMachineRequest) this.xmlToRequest(xml,
				RemoteCtrlMachineRequest.class);
		RemoteCtrlMachineResponse response = remoteCtrlService.doRemoteCtrl(request.getMachineAddr(), request.getOpt());

		return this.responseToXml(response);
	}
}
