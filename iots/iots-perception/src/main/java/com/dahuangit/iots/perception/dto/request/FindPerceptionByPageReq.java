package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

/**
 * 按照分页查询设备
 * 
 * @author 大黄
 * 
 *         2015年3月25日下午12:26:59
 */
public class FindPerceptionByPageReq extends OpRequest {

	/** 设备地址 */
	private String perceptionAddr = null;

	/** 设备类型id */
	private Integer perceptionTypeId = null;

	/** 在线状态 */
	private Integer onlineStatus = null;

	/** 用户id */
	private Integer userId = null;

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
