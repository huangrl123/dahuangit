package com.dahuangit.upgrader.dto;

/**
 * 添加下载记录响应类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:45:55
 */
public class AddDownloadRecordResponse {
	private Boolean success = true;

	private String msg = null;

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

}
