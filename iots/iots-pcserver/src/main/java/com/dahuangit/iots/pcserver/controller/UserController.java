package com.dahuangit.iots.pcserver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.pcserver.dto.request.QueryUserByPageRequest;
import com.dahuangit.iots.pcserver.dto.request.SaveUserRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.service.UserService;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;
import com.dahuangit.iots.perception.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.perception.entry.User;

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
			if (userId != null) {
				this.userService.logout((Integer) userId);
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
	 * 心跳
	 */
	@RequestMapping(value = "/heart", method = RequestMethod.POST)
	@ResponseBody
	public HeartResponse heart(HttpServletRequest httpServletRequest) {
		HeartResponse response = new HeartResponse();

		try {
			Object userId = httpServletRequest.getSession().getAttribute("userId");
			if (userId != null) {
				response = this.userService.heart((Integer) userId);
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * app心跳
	 */
	@RequestMapping(value = "/appHeart", method = RequestMethod.POST)
	@ResponseBody
	public String appHeart(HttpServletRequest httpServletRequest, Integer userId) {
		Response response = new Response();

		try {
			HeartResponse r = this.userService.heart(userId);
			List<NoticeInfo> noticeInfos = r.getNoticeInfos();

			if (null == noticeInfos || noticeInfos.isEmpty()) {
				response.setMsg("");
				return this.responseToXml(response);
			}

			String content = "";
			for (NoticeInfo info : noticeInfos) {
				content = content + "【" + info.getWhen() + "】设备(" + info.getPerceptionAddr() + ")"
						+ info.getParamDesc() + "" + info.getParamValueDesc() + "\r\n";
			}

			response.setMsg(content);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Response addUser(SaveUserRequest request) {
		Response response = new Response();

		try {
			this.userService.addUser(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Response updateUser(SaveUserRequest request) {
		Response response = new Response();

		try {
			this.userService.updateUser(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteUser(HttpServletRequest httpServletRequest, Integer userId) {
		Response response = new Response();

		try {
			Integer curUserId = (Integer) httpServletRequest.getSession().getAttribute("userId");

			if (userId.equals(curUserId)) {
				throw new RuntimeException("不能删除当前登录的用户");
			}

			this.userService.deleteUser(userId);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 跳转到用户查询界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUpdatePasswordPage", method = RequestMethod.GET)
	public String toUpdatePasswordPage(ModelMap map, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		User u = this.userService.getUser(userId);
		map.put("user", u);
		return "/pc/user/updatePasswordPage";
	}

	/**
	 * 跳转到用户查询界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toQueryUserPage", method = RequestMethod.GET)
	public String toQueryUserPage() {
		return "/pc/user/queryUser";
	}

	/**
	 * 通过分页查询用户
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryUserByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<QueryUserByPageResponse> queryUserByPage(QueryUserByPageRequest req) {
		PageQueryResult<QueryUserByPageResponse> queryResult = new PageQueryResult<QueryUserByPageResponse>();

		try {
			queryResult = this.userService.queryUserByPage(req);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryResult;
	}
}
