package com.dahuangit.iots.manager.service;

import com.dahuangit.iots.manager.dto.request.UserLoginRequest;
import com.dahuangit.iots.manager.dto.request.UserLogoutRequest;
import com.dahuangit.iots.manager.dto.response.UserLoginResponse;

public interface UserService {
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	public UserLoginResponse login(UserLoginRequest request);

	/**
	 * 退出登录
	 */
	public void logout(UserLogoutRequest request);
}
