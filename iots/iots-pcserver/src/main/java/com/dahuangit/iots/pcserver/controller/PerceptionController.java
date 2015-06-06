package com.dahuangit.iots.pcserver.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.pcserver.dto.request.AppPerceptionRealtimeVideoPlayRequest;
import com.dahuangit.iots.pcserver.dto.request.AppPerceptionVideoPlayRequest;
import com.dahuangit.iots.pcserver.dto.request.PerceptionStatusPageReq;
import com.dahuangit.iots.pcserver.dto.request.QueryPerceptionParamLogReq;
import com.dahuangit.iots.pcserver.service.UserService;
import com.dahuangit.iots.perception.dto.request.DelPerceptionVideoRequest;
import com.dahuangit.iots.perception.dto.request.FindPerceptionByPageReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionVediaFileByPageRequest;
import com.dahuangit.iots.perception.dto.request.GetRelateUserRequest;
import com.dahuangit.iots.perception.dto.request.PerceptionParamStatusRequest;
import com.dahuangit.iots.perception.dto.request.PerceptionVediaFileUploadNoticeRequest;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.request.SavePerceptionReq;
import com.dahuangit.iots.perception.dto.request.UploadCurStatusParamRequest;
import com.dahuangit.iots.perception.dto.response.GetRelateUserResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionParamStatusQueryResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.PercetionVediaFileResponse;
import com.dahuangit.iots.perception.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.dto.response.VedioResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionParam;
import com.dahuangit.iots.perception.entry.PerceptionType;
import com.dahuangit.iots.perception.service.PerceptionParamService;
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

	@Autowired
	private PerceptionParamService perceptionParamService = null;

	@Autowired
	private UserService userService = null;

	@Value("${perception.ftpDir}")
	private String perceptionFtpDir = null;
	@Value("${local.server.baseUrl}")
	private String serverBaseUrl = null;
	@Value("${perception.realTimePlayerUrl}")
	private String realTimePlayerUrl = null;
	@Value("${rtmpBaseUrl}")
	private String rtmpBaseUrl = null;

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
			if (req.getPerceptionTypeId() == 1) {
				perceptionService.remoteCtrlMachine2j2(req);
			} else {
				Response r = this.perceptionService.remoteOperateMachine(req.getPerceptionId(), req.getParamId(),
						req.getParamValue());
				if (!r.getSuccess()) {
					response.setSuccess(false);
					response.setMsg(r.getMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return response;
	}

	/**
	 * 感知端列表
	 * 
	 * @param opRequest
	 * @return
	 */
	@RequestMapping(value = "/perceptionList", method = RequestMethod.GET)
	public String perceptionList() {
		return "pc/perceptionList";
	}

	/**
	 * 跳转到添加设备界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addPerceptionPage", method = RequestMethod.GET)
	public String addPerceptionPage(ModelMap modelMap) {
		// 设置设备类型列表
		List<PerceptionType> perceptionTypes = this.perceptionService.getAllPerceptionTypes();
		modelMap.put("perceptionTypeList", perceptionTypes);

		return "/pc/perception/addPerception";
	}

	/**
	 * 添加设备
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addPerception", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse addPerception(HttpSession session, SavePerceptionReq req) {
		OpResponse response = new OpResponse();

		try {
			String perceptionAddr = req.getPerceptionAddr();

			Perception p = this.perceptionService.findPerceptionByAddr(perceptionAddr);
			if (null != p) {
				response.setSuccess(false);
				response.setMsg("设备地址已本占用");
				return response;
			}

			req.setUserId((Integer) session.getAttribute("userId"));
			this.perceptionService.addPerception(req);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 修改设备
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePerception", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse updatePerception(SavePerceptionReq req) {
		OpResponse response = new OpResponse();

		try {
			String perceptionAddr = req.getPerceptionAddr();

			Perception p = this.perceptionService.findPerceptionByAddr(perceptionAddr);
			if (null != p && !p.getPerceptionId().equals(req.getPerceptionId())) {
				response.setSuccess(false);
				response.setMsg("设备地址已本占用");
				return response;
			}

			this.perceptionService.updatePerception(req);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 删除设备
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deletePerception", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse deletePerception(Integer perceptionId) {
		OpResponse response = new OpResponse();

		try {
			this.perceptionService.deletePerception(perceptionId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 跳转到设备状态界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perceptionStatusPage", method = RequestMethod.GET)
	public String perceptionStatusPage(ModelMap modelMap, PerceptionStatusPageReq perceptionStatusPageReq) {
		PerceptionParamStatusRequest req = new PerceptionParamStatusRequest();
		req.setPerceptionId(perceptionStatusPageReq.getPerceptionId());

		PerceptionParamStatusQueryResponse perceptionOpResponse = this.perceptionService.queryPerceptionStatus(req);
		modelMap.put("perceptionOpResponse", perceptionOpResponse);

		modelMap.put("realTimePlayerUrl", realTimePlayerUrl);
		modelMap.put("rtmpBaseUrl", rtmpBaseUrl);

		return "/pc/perception/queryPerceptionStatus";
	}

	/**
	 * 跳转到设备状态参数日志查询界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryPerceptionParamLog", method = RequestMethod.GET)
	public String queryPerceptionParamLog(ModelMap modelMap, QueryPerceptionParamLogReq req) {
		modelMap.put("perceptionParamId", req.getPerceptionParamId());
		modelMap.put("perceptionId", req.getPerceptionId());

		ComboboxData data = new ComboboxData();
		try {
			PerceptionOpResponse p = this.perceptionService.getPerception(req.getPerceptionId());
			data = this.perceptionService.getPerceptionParamValueListByParam(req.getPerceptionParamId());
			PerceptionParam perceptionParam = perceptionParamService.getPerceptionParam(req.getPerceptionParamId());
			modelMap.put("perception", p);
			modelMap.put("perceptionParam", perceptionParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelMap.put("root", data.getRoot());

		return "/pc/perception/queryPerceptionParamLog";
	}

	/**
	 * 分页查询感知端设备的某个参数的运行日志
	 * 
	 * @param opRequest
	 * @return
	 */
	@RequestMapping(value = "/findPerceptionParamLogByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionParamLogByPage(
			FindPerceptionRuntimeLogByPageReq req) {

		PageQueryResult<PerceptionRuntimeLogResponse> result = this.perceptionService
				.findPerceptionRuntimeLogByPage(req);

		return result;
	}

	/**
	 * 跳转到查询设备界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryPerceptionPage", method = RequestMethod.GET)
	public String queryPerceptionPage(ModelMap modelMap) {
		// 设置设备类型列表
		List<PerceptionType> perceptionTypes = this.perceptionService.getAllPerceptionTypes();
		modelMap.put("perceptionTypeList", perceptionTypes);

		return "/pc/perception/queryPerception";
	}

	/**
	 * 分页查询感知端
	 * 
	 * @param opRequest
	 * @return
	 */
	@RequestMapping(value = "/getRelateUser", method = RequestMethod.POST)
	@ResponseBody
	public GetRelateUserResponse getRelateUser(Integer perceptionId) {
		GetRelateUserResponse response = new GetRelateUserResponse();

		List<QueryUserByPageResponse> allUserList = this.userService.getAllUser();
		List<QueryUserByPageResponse> relateUserList = this.perceptionService.getRelateUser(perceptionId);

		List<Integer> list = new ArrayList<Integer>();
		for (QueryUserByPageResponse q : relateUserList) {
			list.add(q.getUserId());
		}

		List<QueryUserByPageResponse> delList = new ArrayList<QueryUserByPageResponse>();
		for (QueryUserByPageResponse q : allUserList) {
			if (list.contains(q.getUserId())) {
				delList.add(q);
			}
		}

		allUserList.removeAll(delList);

		response.setAllUserList(allUserList);
		response.setRelateUserList(relateUserList);

		return response;
	}

	/**
	 * 修改设备管理员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePerceptionManagers", method = RequestMethod.POST)
	@ResponseBody
	public Response updatePerceptionManagers(GetRelateUserRequest req) {
		Response response = new Response();

		try {
			List<Integer> list = new ArrayList<Integer>();
			String[] strArr = req.getUserIds().split(",");
			for (String s : strArr) {
				if (null == s || "".equals(s)) {
					continue;
				}

				list.add(Integer.valueOf(s));
			}

			this.perceptionService.updatePerceptionManagers(req.getPerceptionId(), list);
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
	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(HttpSession session,
			FindPerceptionByPageReq opRequest) {
		Integer userId = (Integer) session.getAttribute("userId");
		opRequest.setUserId(userId);
		PageQueryResult<PerceptionOpResponse> result = this.perceptionService.findPerceptionByPage(opRequest);
		return result;
	}

	/**
	 * app分页查询感知端
	 * 
	 * @param opRequest
	 * @return
	 */
	@RequestMapping(value = "/appFindPerceptionByPage", method = RequestMethod.GET)
	public String appFindPerceptionByPage(HttpSession session, ModelMap map, FindPerceptionByPageReq opRequest) {
		session.setAttribute("userId", opRequest.getUserId());
		map.put("userId", opRequest.getUserId());
		return "mobile/appPerceptionList";
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
	public Response fileUploadNotice(String fileInfoXml) {
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

		return response;
	}

	/**
	 * 设备上传当前状态参数到服务器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savePerceptionStatusInfo", method = RequestMethod.POST)
	@ResponseBody
	public String uploadCurStatusParam(UploadCurStatusParamRequest request) {
		Response response = new Response();

		try {
			if (null == request.getPerceptionAddr()) {
				response.setSuccess(false);
				response.setMsg("设备地址[perceptionAddr]不能为空");
				return this.responseToXml(response);
			}

			if (null == request.getPerceptionStatusInfoXml()) {
				response.setSuccess(false);
				response.setMsg("设备状态参数XML字符串[perceptionStatusInfoXml]不能为空");
				return this.responseToXml(response);
			}

			response = this.perceptionService.uploadCurStatusParam(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取设备的视频文件列表
	 * 
	 * @param perceptionAddr
	 * @return
	 */
	@RequestMapping(value = "/getPerceptionVedioList", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<VedioResponse> getPerceptionVedioList(HttpServletRequest request, String perceptionAddr) {
		PageQueryResult<VedioResponse> queryResult = new PageQueryResult<VedioResponse>();

		List<VedioResponse> list = new ArrayList<VedioResponse>();

		File dir = new File(this.perceptionFtpDir + "\\" + perceptionAddr);
		File[] files = dir.listFiles();
		String url = serverBaseUrl + request.getContextPath();
		for (File f : files) {
			VedioResponse response = new VedioResponse();
			response.setFileName(f.getName());
			response.setUrl(url + "/video/" + perceptionAddr + "/" + f.getName());
			list.add(response);
		}

		queryResult.setRows(list);
		queryResult.setTotal(list.size());

		return queryResult;
	}

	/**
	 * 获取设备的视频文件列表
	 * 
	 * @param perceptionAddr
	 * @return
	 */
	@RequestMapping(value = "/appGetPerceptionVedioList", method = RequestMethod.GET)
	public String appGetPerceptionVedioList(HttpServletRequest request, ModelMap map, String perceptionAddr,
			String perceptionName, Integer userId, Integer perceptionId) {
		PageQueryResult<VedioResponse> queryResult = new PageQueryResult<VedioResponse>();

		List<VedioResponse> list = new ArrayList<VedioResponse>();

		File dir = new File(this.perceptionFtpDir + "\\" + perceptionAddr);
		File[] files = dir.listFiles();
		String url = serverBaseUrl + request.getContextPath();
		for (File f : files) {
			VedioResponse response = new VedioResponse();
			response.setFileName(f.getName());
			response.setUrl(url + "/video/" + perceptionAddr + "/" + f.getName());
			list.add(response);
		}

		queryResult.setRows(list);
		queryResult.setTotal(list.size());

		try {
			perceptionName = new String(perceptionName.getBytes("ISO8859-1"), "UTF-8");
			map.put("perceptionName", perceptionName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		map.put("perceptionAddr", perceptionAddr);
		map.put("perceptionId", perceptionId);
		map.put("userId", userId);

		map.put("queryResult", queryResult);

		return "mobile/appPerceptionVideoList";
	}

	/**
	 * 从ftp服务器上删除设备的视频文件
	 * 
	 */
	@RequestMapping(value = "/delPerceptionVideo", method = RequestMethod.POST)
	@ResponseBody
	public Response delPerceptionVideo(DelPerceptionVideoRequest req) {
		Response response = new Response();

		try {
			this.perceptionService.delPerceptionVideo(req);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 下载设备视频文件
	 * 
	 * @param request
	 * @param response
	 * @param perceptionAddr
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadVideoFile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadVideoFile(HttpServletRequest request, HttpServletResponse response,
			String perceptionAddr, String fileName) throws IOException {

		byte[] fileData = null;
		HttpHeaders headers = null;
		try {
			String filePathName = perceptionFtpDir + "/" + perceptionAddr + "/" + fileName;
			fileData = FileUtils.readFileToByteArray(new File(filePathName));

			headers = new HttpHeaders();

			MediaType mt = new MediaType("application", "octet-stream");
			headers.setContentType(mt);

			headers.setContentDispositionFormData("attachment", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
	}

	/**
	 * 跳转到视频播放界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appPerceptionVideoPlay", method = RequestMethod.GET)
	public String appPerceptionVideoPlay(ModelMap map, AppPerceptionVideoPlayRequest request) {

		try {
			String perceptionName = new String(request.getPerceptionName().getBytes("ISO8859-1"), "UTF-8");
			request.setPerceptionName(perceptionName);
			map.put("req", request);
		} catch (Exception e) {
		}

		return "mobile/appPerceptionVideoPlay";
	}

	/**
	 * 跳转到实时视频播放界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appPerceptionRealtimeVideoPlay", method = RequestMethod.GET)
	public String appPerceptionRealtimeVideoPlay(ModelMap map, AppPerceptionRealtimeVideoPlayRequest request) {

		try {
			String perceptionName = new String(request.getPerceptionName().getBytes("ISO8859-1"), "UTF-8");
			request.setPerceptionName(perceptionName);
			map.put("req", request);

			String rtmpUrl = realTimePlayerUrl + "?baseRtmpUrl=" + rtmpBaseUrl + "&rtmpPalyName="
					+ request.getPerceptionAddr();
			map.put("realTimePlayerUrl", rtmpUrl);
		} catch (Exception e) {
		}

		return "mobile/appPerceptionRealtimeVideoPlay";
	}

}
