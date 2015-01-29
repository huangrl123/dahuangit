package com.dahuangit.water.proxy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.util.date.DateUtils;
import com.dahuangit.water.proxy.dto.request.JiankongRequest;
import com.dahuangit.water.proxy.dto.request.LoginRequest;
import com.dahuangit.water.proxy.dto.request.ShouzhiRequest;
import com.dahuangit.water.proxy.dto.request.SubmitYongshuiRequest;
import com.dahuangit.water.proxy.dto.request.SunyiRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRecordRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRequest;
import com.dahuangit.water.proxy.dto.request.YujingRequest;
import com.dahuangit.water.proxy.dto.response.ChartInfo;
import com.dahuangit.water.proxy.dto.response.GetLdListResponse;
import com.dahuangit.water.proxy.dto.response.GetProjectListResponse;
import com.dahuangit.water.proxy.dto.response.JiankongResponse;
import com.dahuangit.water.proxy.dto.response.LoginResponse;
import com.dahuangit.water.proxy.dto.response.RecentYearSemesterMonthInfo;
import com.dahuangit.water.proxy.dto.response.RecentYearSemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterMonthInfo;
import com.dahuangit.water.proxy.dto.response.SemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterSumResponse;
import com.dahuangit.water.proxy.dto.response.ShouzhiInfo;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.SubmitYongshuiResponse;
import com.dahuangit.water.proxy.dto.response.SunyiAjaxDataResponse;
import com.dahuangit.water.proxy.dto.response.YongshuiRecordResponse;
import com.dahuangit.water.proxy.dto.response.YujingInfo;
import com.dahuangit.water.proxy.dto.response.YujingResponse;
import com.dahuangit.water.proxy.service.WaterService;

@Controller
@RequestMapping("/mobile")
public class WaterController extends BaseController {

	@Autowired
	private WaterService waterService = null;

	/**
	 * 进入首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/functionList", method = RequestMethod.GET)
	public String functionList(ModelMap modelMap) {
		return "functionList";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "login";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap modelMap, LoginRequest request) {
		return "functionList";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
	@ResponseBody
	public Response submitLogin(ModelMap modelMap, HttpServletRequest httpServletRequest, LoginRequest request) {
		LoginResponse response = new LoginResponse();

		try {
			response = waterService.login(request);

			httpServletRequest.getSession().setAttribute("systemId", response.getSystemId());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProjectList", method = RequestMethod.GET)
	public String getProjectList(ModelMap modelMap, HttpServletRequest httpServletRequest) {
		GetProjectListResponse response = new GetProjectListResponse();

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLdList", method = RequestMethod.POST)
	@ResponseBody
	public GetLdListResponse getLdList(String projectId, HttpServletRequest httpServletRequest) {
		GetLdListResponse response = new GetLdListResponse();

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			response = waterService.getLdList(systemId, projectId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 获取收支情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhi", method = RequestMethod.GET)
	public String shouzhi(ModelMap modelMap, HttpServletRequest httpServletRequest, ShouzhiRequest request) {
		ShouzhiResponse response = new ShouzhiResponse();

		try {
			if (null == request.getBeginTime()) {
				String beginDT = DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd");
				request.setBeginTime(beginDT);
			}

			if (null == request.getEndTime()) {
				String endDT = DateUtils.format(new Date(), "yyyy-MM-dd");
				request.setEndTime(endDT);
			}

			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			request.setSystemId(systemId);

			response = waterService.shouzhi(request);
			Map<String, List<ShouzhiInfo>> proShouzhi = new HashMap<String, List<ShouzhiInfo>>();
			List<ShouzhiInfo> shouzhiInfos = response.getShouzhiInfos();

			for (ShouzhiInfo info : shouzhiInfos) {
				String key = info.getProjectName();
				if (proShouzhi.containsKey(key)) {
					proShouzhi.get(key).add(info);
				}

				else {
					List<ShouzhiInfo> list = new ArrayList<ShouzhiInfo>();
					list.add(info);
					proShouzhi.put(key, list);
				}
			}

			modelMap.put("shouzhiMap", proShouzhi);
			modelMap.put("request", request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return "shouzhi";
	}

	/**
	 * 获取收支情况查询界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhiQuery", method = RequestMethod.GET)
	public String shouzhiQuery(ModelMap modelMap, HttpServletRequest httpServletRequest) {
		GetProjectListResponse response = null;

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}

		return "shouzhiQuery";
	}

	/**
	 * 个人账号信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(ModelMap modelMap, ShouzhiRequest request) {
		return "account";
	}

	/**
	 * 设备预警统计表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yujing", method = RequestMethod.GET)
	public String yujing(ModelMap modelMap, HttpServletRequest httpServletRequest, YujingRequest request) {
		YujingResponse response = new YujingResponse();

		try {
			if (null == request.getBeginTime()) {
				String beginDT = DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd");
				request.setBeginTime(beginDT);
			}

			if (null == request.getEndTime()) {
				String endDT = DateUtils.format(new Date(), "yyyy-MM-dd");
				request.setEndTime(endDT);
			}

			if (null == request.getProjectId()) {
				request.setProjectId("0");
			}

			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			request.setSystemId(systemId);

			response = waterService.yujing(request);
			Map<String, List<YujingInfo>> yujingMap = new HashMap<String, List<YujingInfo>>();
			List<YujingInfo> yujingInfos = response.getYujingInfos();

			for (YujingInfo info : yujingInfos) {
				String key = info.getProjectName();
				if (yujingMap.containsKey(key)) {
					yujingMap.get(key).add(info);
				}

				else {
					List<YujingInfo> list = new ArrayList<YujingInfo>();
					list.add(info);
					yujingMap.put(key, list);
				}
			}

			modelMap.put("yujingMap", yujingMap);
			modelMap.put("request", request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return "yujing";
	}

	/**
	 * 设备预警查询界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yujingQuery", method = RequestMethod.GET)
	public String yujingQuery(ModelMap modelMap, HttpServletRequest httpServletRequest, ShouzhiRequest request) {
		GetProjectListResponse response = null;
		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			if (null == request.getProjectId()) {
				request.setProjectId("0");
			}

			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}
		return "yujingQuery";
	}

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jiankong", method = RequestMethod.GET)
	public String jiankong(ModelMap modelMap, JiankongRequest request) {
		JiankongResponse response = null;
		//
		// try {
		// response = waterService.jiankong(request);
		// } catch (Exception e) {
		// response.setSuccess(false);
		// response.setMsg(e.getMessage());
		// e.printStackTrace();
		// }
		//
		// return this.responseToXml(response);

		return "jiankong";
	}

	/**
	 * 设备预警查询界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jiankongQuery", method = RequestMethod.GET)
	public String jiankongQuery(ModelMap modelMap, HttpServletRequest httpServletRequest, ShouzhiRequest request) {
		GetProjectListResponse response = null;
		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			if (null == request.getProjectId()) {
				request.setProjectId("0");
			}

			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}

		return "jiankongQuery";
	}

	/**
	 * 获取损益情况查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sunyiQuery", method = RequestMethod.GET)
	public String sunyiQuery(ModelMap modelMap, HttpServletRequest httpServletRequest) {
		GetProjectListResponse response = null;
		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}
		return "sunyiQuery";
	}

	/**
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sunyi", method = RequestMethod.GET)
	public String sunyi(ModelMap modelMap, HttpServletRequest httpServletRequest, SunyiRequest request) {
		try {
			String projectId = request.getProjectId();
			String projectName = new String(request.getProjectName().getBytes("ISO8859-1"), "UTF-8");

			// 学期概况
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			SemesterSumResponse semesterSumResponse = this.waterService.getSemesterSum(systemId, projectId);
			modelMap.put("semesterSumInfo", semesterSumResponse.getSemesterSumInfo());

			modelMap.put("projectId", request.getProjectId());
			modelMap.put("projectName", projectName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "sunyi";
	}

	/**
	 * 获取损益ajax数据(包括了学期月份的数据库和近三年数据)
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/getSunyiAjaxData", method = RequestMethod.POST)
	@ResponseBody
	public SunyiAjaxDataResponse getSunyiAjaxData(String projectId, HttpServletRequest httpServletRequest) {
		SunyiAjaxDataResponse response = new SunyiAjaxDataResponse();
		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			SemesterMonthResponse semesterMonthResponse = this.waterService.getSemesterMonth(systemId, projectId);

			ChartInfo semesterMonthChartInfo = new ChartInfo();
			for (SemesterMonthInfo semesterMonthInfo : semesterMonthResponse.getSemesterMonthInfos()) {
				semesterMonthChartInfo.getCategories().add(semesterMonthInfo.getMonth());
				semesterMonthChartInfo.getData().add(semesterMonthInfo.getSumSy());
			}
			response.setSemesterMonthChartInfo(semesterMonthChartInfo);

			RecentYearSemesterMonthResponse recentYearSemesterMonthResponse = this.waterService
					.getRecentYearSemesterMonth(systemId, projectId);

			ChartInfo recentYearSemesterMonthChartInfo = new ChartInfo();
			for (RecentYearSemesterMonthInfo info : recentYearSemesterMonthResponse.getRecentYearSemesterMonthInfos()) {
				recentYearSemesterMonthChartInfo.getCategories().add(info.getMonth());
				recentYearSemesterMonthChartInfo.getData().add(info.getSumSy());
			}
			response.setRecentYearSemesterMonthChartInfo(recentYearSemesterMonthChartInfo);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yongshui", method = RequestMethod.GET)
	public String yongshui(ModelMap modelMap, HttpServletRequest httpServletRequest, YongshuiRequest request) {
		GetProjectListResponse response = null;

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();

			response = waterService.getProjectList(systemId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}

		return "yongshui";
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getYongshuiRecord", method = RequestMethod.POST)
	@ResponseBody
	public YongshuiRecordResponse getYongshuiRecord(YongshuiRecordRequest request, HttpServletRequest httpServletRequest) {
		YongshuiRecordResponse response = new YongshuiRecordResponse();

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			request.setSystemId(systemId);

			response = waterService.yongshuiRecord(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 提交用水记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitYongshui", method = RequestMethod.POST)
	@ResponseBody
	public SubmitYongshuiResponse submitYongshui(SubmitYongshuiRequest request, HttpServletRequest httpServletRequest) {
		SubmitYongshuiResponse response = new SubmitYongshuiResponse();

		try {
			String systemId = httpServletRequest.getSession().getAttribute("systemId").toString();
			request.setSystemId(systemId);
			
			Response r = waterService.yongshui(request);

			if (r.isSuccess()) {
				YongshuiRecordRequest yongshuiRecordRequest = new YongshuiRecordRequest();
				yongshuiRecordRequest.setProjectId(request.getProjectId());
				yongshuiRecordRequest.setLdId(request.getLdId());

				yongshuiRecordRequest.setSystemId(systemId);

				YongshuiRecordResponse yongshuiRecordResponse = waterService.yongshuiRecord(yongshuiRecordRequest);
				response.setYongshuiRecords(yongshuiRecordResponse.getYongshuiRecords());
			} else {
				response.setSuccess(false);
				response.setMsg(r.getMsg());
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

}