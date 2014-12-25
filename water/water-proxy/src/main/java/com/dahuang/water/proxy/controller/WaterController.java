package com.dahuang.water.proxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dahuang.water.proxy.dto.request.JiankongRequest;
import com.dahuang.water.proxy.dto.request.LoginRequest;
import com.dahuang.water.proxy.dto.request.ShouzhiRequest;
import com.dahuang.water.proxy.dto.request.SunyiRequest;
import com.dahuang.water.proxy.dto.request.YongshuiRequest;
import com.dahuang.water.proxy.dto.response.GetLdListResponse;
import com.dahuang.water.proxy.dto.response.GetProjectListResponse;
import com.dahuang.water.proxy.dto.response.JiankongResponse;
import com.dahuang.water.proxy.dto.response.LoginResponse;
import com.dahuang.water.proxy.dto.response.ShouzhiResponse;
import com.dahuang.water.proxy.dto.response.SunyiResponse;
import com.dahuang.water.proxy.service.WaterService;
import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;

@Controller
@RequestMapping("/water")
public class WaterController extends BaseController {

	@Autowired
	private WaterService waterService = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(LoginRequest request) {
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
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST)
	public String getProjectList() {
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
	@RequestMapping(value = "/getLdList", method = RequestMethod.POST)
	public String getLdList() {
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
	 * 获取收资情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhi", method = RequestMethod.POST)
	public String shouzhi(ShouzhiRequest request) {
		ShouzhiResponse response = null;

		try {
			response = waterService.shouzhi(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jiankong", method = RequestMethod.POST)
	public String jiankong(JiankongRequest request) {
		JiankongResponse response = null;

		try {
			response = waterService.jiankong(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sunyi", method = RequestMethod.POST)
	public String sunyi(SunyiRequest request) {
		SunyiResponse response = null;

		try {
			response = waterService.sunyi(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yongshui", method = RequestMethod.POST)
	public String yongshui(YongshuiRequest request) {
		Response response = null;

		try {
			response = waterService.yongshui(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}
}
