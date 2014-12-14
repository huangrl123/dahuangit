package com.dahuangit.iots.manager.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 用户登录请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:26:11
 */
public class UserLoginRequest extends Request {
	private String userName = null;

	private String password = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
