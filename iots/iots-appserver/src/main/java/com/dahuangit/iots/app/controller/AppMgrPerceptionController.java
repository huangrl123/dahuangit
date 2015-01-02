package com.dahuangit.iots.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionRuntimeLogListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.service.AppQueryPerceptionService;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
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
	 * 跳转到app登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appLogin", method = RequestMethod.GET)
	public String appLogin() {
		return "mobile/appLogin";
	}

	/**
	 * 跳转到app 感知端列表界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appPerceptionList", method = RequestMethod.GET)
	public String appPerceptionList(HttpServletRequest httpServletRequest, ModelMap modelMap,
			AppGetPerceptionListRequest request) {

		AppGetPerceptionListResponse response = new AppGetPerceptionListResponse();

		try {
			Integer userId = Integer.valueOf(httpServletRequest.getSession().getAttribute("curUserId").toString());
			request.setUserId(userId);
			response = this.appQueryPerceptionService.appGetPerceptionList(request);

			modelMap.put("perceptionList", response.getPerceptionDtos());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return "mobile/appPerceptionList";
	}

	/**
	 * 跳转到app 感知端功能列表界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appPerceptionFunctionList", method = RequestMethod.GET)
	public String appPerceptionFunctionList(ModelMap modelMap, Integer perceptionId) {
		// 获取感知端基本信息
		PerceptionOpResponse perceptionOpResponse = this.perceptionService.getPerception(perceptionId);
		modelMap.put("perceptionOpResponse", perceptionOpResponse);

		// 获取感知端状态列表
		if (perceptionOpResponse.getPerceptionTypeId() == 1) {// 2+2
			RemoteQuery2j2MachineResponse query2j2MachineResponse = this.perceptionService
					.remoteQuery2j2Machine(perceptionId);
			modelMap.put("query2j2MachineResponse", query2j2MachineResponse);

			// 获取状态下拉选列表
			// 电机1旋转状态
			ComboboxData rotateStatus_machine1_combobox = this.perceptionService
					.getPerceptionParamValueListByParam(179);
			modelMap.put("rotateStatus_machine1_combobox", rotateStatus_machine1_combobox);

			// 电机2旋转状态
			ComboboxData rotateStatus_machine2_combobox = this.perceptionService
					.getPerceptionParamValueListByParam(187);
			modelMap.put("rotateStatus_machine2_combobox", rotateStatus_machine2_combobox);

			// i2c状态
			ComboboxData i2cStatus_combobox = this.perceptionService.getPerceptionParamValueListByParam(182);
			modelMap.put("i2cStatus_combobox", i2cStatus_combobox);

			return "mobile/appPerceptionFunctionList";

		}

		else {
			return null;
		}
	}

	/**
	 * 查询2+2设备状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	@RequestMapping(value = "/query2j2Machine", method = RequestMethod.POST)
	@ResponseBody
	public RemoteQuery2j2MachineResponse query2j2Machine(Integer perceptionId) {
		RemoteQuery2j2MachineResponse response = this.perceptionService.remoteQuery2j2Machine(perceptionId);
		response.setHex(null);
		return response;
	}

	/**
	 * 跳转到app 感知端日志信息界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appPerceptionLog", method = RequestMethod.GET)
	public String appPerceptionLog(ModelMap modelMap, Integer perceptionId, Integer reqPage) {

		FindPerceptionRuntimeLogByPageReq req = new FindPerceptionRuntimeLogByPageReq();

		if (null == reqPage) {
			reqPage = 1;
		}

		Integer start = (reqPage - 1) * req.getLimit();
		req.setStart(start);
		req.setPerceptionId(perceptionId);
		
		PageQueryResult<PerceptionRuntimeLogResponse> result = this.perceptionService
				.findPerceptionRuntimeLogByPage(req);

		double d = (double) result.getTotalCount() / (double) req.getLimit();
		Integer totalPage = (int) Math.ceil(d);

		modelMap.put("perceptionId", perceptionId);
		modelMap.put("curPage", reqPage);
		modelMap.put("totalPage", totalPage);
		modelMap.put("PerceptionRuntimeLogList", result.getResults());
		
		return "mobile/appPerceptionLog";
	}

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
			FindPerceptionRuntimeLogByPageReq req = new FindPerceptionRuntimeLogByPageReq();
			PageQueryResult<PerceptionRuntimeLogResponse> result = this.perceptionService
					.findPerceptionRuntimeLogByPage(req);
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
			response.setHex(null);
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
