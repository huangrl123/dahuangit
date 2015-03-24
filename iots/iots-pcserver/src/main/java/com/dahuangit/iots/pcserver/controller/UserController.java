package com.dahuangit.iots.pcserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLogoutRequest;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.service.UserService;
import com.dahuangit.util.CookieUtils;

/**
 * 用户controller
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午10:40:00
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {

	@Autowired
	private UserService userService = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public UserLoginResponse login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			UserLoginRequest request) {
		UserLoginResponse response = new UserLoginResponse();

		try {
			response = userService.login(request);
			response = userService.login(request);
			CookieUtils.addCookie(httpServletResponse, "curUserId", response.getUserId().toString(), -1);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Response logout(UserLogoutRequest request) {
		Response response = new Response();

		try {
			this.userService.logout(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
