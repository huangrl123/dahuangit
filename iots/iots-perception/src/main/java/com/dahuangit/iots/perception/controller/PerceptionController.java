package com.dahuangit.iots.perception.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.opm.request.OpRequest;
import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.opm.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.opm.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.oxm.response.RemoteQueryMachineResponse;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.util.log.Log4jUtils;

@Controller
@RequestMapping("/perception")
public class PerceptionController extends BaseController {

	private static final Logger log = Log4jUtils.getLogger(PerceptionController.class);

	@Autowired
	private RemoteQueryService remoteQueryService = null;

	@Autowired
	private PerceptionService perceptionService = null;

	@RequestMapping(value = "/remoteQueryPerception", method = RequestMethod.POST)
	@ResponseBody
	public RemoteQueryMachineResponse remoteQueryPerception(Integer perceptionId) throws Exception {

		RemoteQueryMachineResponse response = new RemoteQueryMachineResponse();
		try {
			response = remoteQueryService.doRemoteQuery(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = "/remoteCtrlPerception", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse remoteCtrlPerception(RemoteCtrlPerceptionRequest req) {
		OpResponse response = new OpResponse();

		try {
			perceptionService.remoteCtrlPerception(req);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/findPerceptionByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(OpRequest opRequest) {
		PageQueryResult<PerceptionOpResponse> result = this.perceptionService.findPerceptionByPage(
				opRequest.getStart(), opRequest.getLimit());
		return result;
	}

	@RequestMapping(value = "/findPerceptionRuntimeLogByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req) {
		PageQueryResult<PerceptionRuntimeLogResponse> result = this.perceptionService
				.findPerceptionRuntimeLogByPage(req);
		return result;
	}

	@RequestMapping(value = "/getPerceptionParamListByTypeId", method = RequestMethod.POST)
	@ResponseBody
	public ComboboxData getPerceptionParamValueListByParam(Integer paramId) {
		ComboboxData data = new ComboboxData();
		try {
			data = this.perceptionService.getPerceptionParamValueListByParam(paramId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
