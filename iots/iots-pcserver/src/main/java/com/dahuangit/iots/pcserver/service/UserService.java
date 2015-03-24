package com.dahuangit.iots.pcserver.service;

import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLogoutRequest;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;

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
