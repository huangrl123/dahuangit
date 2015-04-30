package com.dahuangit.iots.perception.dto.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.dahuangit.base.dto.Response;
import com.dahuangit.util.bean.dto.Dto;
import com.dahuangit.util.bean.dto.DtoField;
import com.dahuangit.util.spring.JsonDateSerializer;

@Dto
public class QueryUserByPageResponse extends Response {

	private Integer userId = null;

	private String userName = null;

	private String userAbbr = null;

	private String password = null;

	private String remark = null;

	private Boolean isOnline = false;

	@DtoField(ignore = true)
	private String lastLoginTime = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
