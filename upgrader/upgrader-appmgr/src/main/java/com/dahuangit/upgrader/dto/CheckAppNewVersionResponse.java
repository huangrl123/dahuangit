package com.dahuangit.upgrader.dto;

/**
 * 检查是否有新的app版本
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午11:57:06
 */
public class CheckAppNewVersionResponse {
	private Boolean success = true;

	private String msg = null;

	private String appUrl = null;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

}
