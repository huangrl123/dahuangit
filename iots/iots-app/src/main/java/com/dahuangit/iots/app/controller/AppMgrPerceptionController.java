package com.dahuangit.iots.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionRuntimeLogListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.service.AppQueryPerceptionService;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery6j6MachineResponse;
import com.dahuangit.iots.perception.service.PerceptionService;

/**
 * app端查询感知端情况
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月11日 下午11:38:31
 */
@Controller
@RequestMapping("/appMgrPerceptionController")
public class AppMgrPerceptionController extends BaseController {

	@Autowired
	private AppQueryPerceptionService appQueryPerceptionService = null;

	@Autowired
	private PerceptionService perceptionService = null;

	/**
	 * app获取感知端列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appGetPerceptionList", method = RequestMethod.POST)
	@ResponseBody
	public String appGetPerceptionList(AppGetPerceptionListRequest request) {
		AppGetPerceptionListResponse response = new AppGetPerceptionListResponse();

		try {
			response = this.appQueryPerceptionService.appGetPerceptionList(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * app获取感知端运行日志列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appGetPerceptionRuntimeLogList", method = RequestMethod.POST)
	@ResponseBody
	public String appGetPerceptionRuntimeLogList(AppGetPerceptionRuntimeLogListRequest request) {
		AppGetPerceptionRuntimeLogListResponse response = new AppGetPerceptionRuntimeLogListResponse();

		try {
			response = this.appQueryPerceptionService.appGetPerceptionRuntimeLogList(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 2+2远程控制
	 * 
	 * @param perceptionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remoteQuery2j2Machine", method = RequestMethod.POST)
	@ResponseBody
	public String remoteQuery2j2Machine(Integer perceptionId) throws Exception {

		RemoteQuery2j2MachineResponse response = new RemoteQuery2j2MachineResponse();

		try {
			response = perceptionService.remoteQuery2j2Machine(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 6+6远程控制
	 * 
	 * @param perceptionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remoteQuery6j6Machine", method = RequestMethod.POST)
	@ResponseBody
	public String remoteQuery6j6Machine(Integer perceptionId) throws Exception {
		RemoteQuery6j6MachineResponse response = new RemoteQuery6j6MachineResponse();

		try {
			response = perceptionService.remoteQuery6j6Machine(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 感知端远程控制
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/remoteCtrlPerception", method = RequestMethod.POST)
	@ResponseBody
	public String remoteCtrlPerception(RemoteCtrlPerceptionRequest req) {
		Response response = new Response();

		try {
			perceptionService.remoteCtrlMachine(req.getPerceptionId(), req.getOpt());
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return this.responseToXml(response);
	}
}
