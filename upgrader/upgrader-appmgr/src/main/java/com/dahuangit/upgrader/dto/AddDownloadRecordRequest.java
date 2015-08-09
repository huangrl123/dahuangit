package com.dahuangit.upgrader.dto;

/**
 * 添加下载记录请求dto类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:36:31
 */
public class AddDownloadRecordRequest {

	/** 客户端ip */
	private String clientAddr = null;

	public String getClientAddr() {
		return clientAddr;
	}

	public void setClientAddr(String clientAddr) {
		this.clientAddr = clientAddr;
	}

}
