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
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.service.UserService;

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
			httpServletRequest.getSession().setAttribute("userName", request.getUserName());
			httpServletRequest.getSession().setAttribute("userId", response.getUserId());
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
	public Response logout(HttpServletRequest httpServletRequest) {
		Response response = new Response();

		try {
			Object userId = httpServletRequest.getSession().getAttribute("userId");
			if(userId != null) {
				this.userService.logout((Integer)userId);
			}
			
			httpServletRequest.getSession().setAttribute("userName", null);
			httpServletRequest.getSession().setAttribute("userId", null);
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
	@RequestMapping(value = "/heart", method = RequestMethod.POST)
	@ResponseBody
	public HeartResponse heart(HttpServletRequest httpServletRequest) {
		HeartResponse response = new HeartResponse();

		try {
			Object userId = httpServletRequest.getSession().getAttribute("userId");
			if(userId != null) {
				response = this.userService.heart((Integer)userId);
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
