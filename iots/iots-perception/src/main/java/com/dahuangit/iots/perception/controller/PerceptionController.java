package com.dahuangit.iots.perception.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.opm.request.OpRequest;
import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionVediaFileByPageRequest;
import com.dahuangit.iots.perception.dto.request.PerceptionVediaFileUploadNoticeRequest;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.PercetionVediaFileResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery6j6MachineResponse;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.service.PerceptionVediaService;
import com.dahuangit.util.log.Log4jUtils;

@Controller
@RequestMapping("/perception")
public class PerceptionController extends BaseController {

	private static final Logger log = Log4jUtils.getLogger(PerceptionController.class);

	@Autowired
	private PerceptionService perceptionService = null;

	@Autowired
	private PerceptionVediaService perceptionVediaService = null;

	/**
	 * 2+2远程控制
	 * 
	 * @param perceptionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remoteQuery2j2Machine", method = RequestMethod.POST)
	@ResponseBody
	public RemoteQuery2j2MachineResponse remoteQuery2j2Machine(Integer perceptionId) throws Exception {

		RemoteQuery2j2MachineResponse response = new RemoteQuery2j2MachineResponse();
		try {
			response = perceptionService.remoteQuery2j2Machine(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
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
	public RemoteQuery6j6MachineResponse remoteQuery6j6Machine(Integer perceptionId) throws Exception {
		RemoteQuery6j6MachineResponse response = new RemoteQuery6j6MachineResponse();

		try {
			response = perceptionService.remoteQuery6j6Machine(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 感知端远程控制
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/remoteCtrlPerception", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse remoteCtrlPerception(RemoteCtrlPerceptionRequest req) {
		OpResponse response = new OpResponse();

		try {
			perceptionService.remoteCtrlMachine(req.getPerceptionId(), req.getOpt());
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return response;
	}

	/**
	 * 分页查询感知端
	 * 
	 * @param opRequest
	 * @return
	 */
	@RequestMapping(value = "/findPerceptionByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(OpRequest opRequest) {
		PageQueryResult<PerceptionOpResponse> result = this.perceptionService.findPerceptionByPage(
				opRequest.getStart(), opRequest.getLimit());
		return result;
	}

	/**
	 * 分页查询感知端运行日志
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findPerceptionRuntimeLogByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req) {
		PageQueryResult<PerceptionRuntimeLogResponse> result = this.perceptionService
				.findPerceptionRuntimeLogByPage(req);
		return result;
	}

	/**
	 * 通过参数id查询感知端状态的下拉列表值
	 * 
	 * @param paramId
	 * @return
	 */
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

	/**
	 * 分页查询感知端视频文件
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findPerceptionVediaFileByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PercetionVediaFileResponse> findPerceptionVediaFileByPage(
			FindPerceptionVediaFileByPageRequest req) {
		PageQueryResult<PercetionVediaFileResponse> result = this.perceptionVediaService.findPerceptionByPage(
				req.getPerceptionId(), req.getStart(), req.getLimit());
		return result;
	}

	/**
	 * 感知端文件上传通知
	 * 
	 * @param fileInfoXml
	 * @return
	 */
	@RequestMapping(value = "/fileUploadNotice", method = RequestMethod.POST)
	@ResponseBody
	public String fileUploadNotice(String fileInfoXml) {
		Response response = new Response();

		try {
			PerceptionVediaFileUploadNoticeRequest req = (PerceptionVediaFileUploadNoticeRequest) this.xmlToRequest(
					fileInfoXml, PerceptionVediaFileUploadNoticeRequest.class);

			perceptionVediaService.fileUploadNotice(req);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return this.responseToXml(response);
	}
}
