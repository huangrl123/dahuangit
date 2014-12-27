package com.dahuangit.water.proxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.water.proxy.dto.request.JiankongRequest;
import com.dahuangit.water.proxy.dto.request.LoginRequest;
import com.dahuangit.water.proxy.dto.request.ShouzhiRequest;
import com.dahuangit.water.proxy.dto.request.SunyiRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRequest;
import com.dahuangit.water.proxy.dto.response.GetLdListResponse;
import com.dahuangit.water.proxy.dto.response.GetProjectListResponse;
import com.dahuangit.water.proxy.dto.response.JiankongResponse;
import com.dahuangit.water.proxy.dto.response.LoginResponse;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.SunyiResponse;
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
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String login(ModelMap modelMap) {
		return "functionList";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap modelMap, LoginRequest request) {
		LoginResponse response = null;

		try {
			response = waterService.login(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProjectList", method = RequestMethod.GET)
	public String getProjectList(ModelMap modelMap) {
		GetProjectListResponse response = null;

		try {
			response = waterService.getProjectList();
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
	@RequestMapping(value = "/getLdList", method = RequestMethod.GET)
	public String getLdList(ModelMap modelMap) {
		GetLdListResponse response = null;

		try {
			response = waterService.getLdList();
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取收支情况查询界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhiQuery", method = RequestMethod.GET)
	public String shouzhiQuery(ModelMap modelMap) {
		GetProjectListResponse response = null;
		try {
			response = waterService.getProjectList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != response && !response.getProjectInfos().isEmpty()) {
			modelMap.put("projectInfos", response.getProjectInfos());
		}

		return "shouzhiQuery";
	}

	/**
	 * 获取收支情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhi", method = RequestMethod.GET)
	public String shouzhi(ModelMap modelMap, ShouzhiRequest request) {
		ShouzhiResponse response = null;

		// try {
		// response = waterService.shouzhi(request);
		// } catch (Exception e) {
		// response.setSuccess(false);
		// response.setMsg(e.getMessage());
		// e.printStackTrace();
		// }
		//
		// return this.responseToXml(response);

		return "shouzhi";
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
	 * 获取损益情况查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sunyiQuery", method = RequestMethod.GET)
	public String sunyiQuery(ModelMap modelMap) {
		GetProjectListResponse response = null;
		try {
			response = waterService.getProjectList();
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
	public String sunyi(ModelMap modelMap, SunyiRequest request) {
		SunyiResponse response = null;

		// try {
		// response = waterService.sunyi(request);
		// } catch (Exception e) {
		// response.setSuccess(false);
		// response.setMsg(e.getMessage());
		// e.printStackTrace();
		// }
		//
		// return this.responseToXml(response);

		return "sunyi";
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yongshui", method = RequestMethod.GET)
	public String yongshui(ModelMap modelMap, YongshuiRequest request) {
		Response response = null;

		// try {
		// response = waterService.yongshui(request);
		// } catch (Exception e) {
		// response.setSuccess(false);
		// response.setMsg(e.getMessage());
		// e.printStackTrace();
		// }
		//
		// return this.responseToXml(response);

		return "yongshui";
	}
}
