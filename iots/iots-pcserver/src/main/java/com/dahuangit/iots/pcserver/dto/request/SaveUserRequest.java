package com.dahuangit.iots.pcserver.dto.request;

public class SaveUserRequest {

	private Integer userId = null;

	private String userName = null;

	private String userAbbr = null;

	private String password = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAbbr() {
		return userAbbr;
	}

	public void setUserAbbr(String userAbbr) {
		this.userAbbr = userAbbr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
