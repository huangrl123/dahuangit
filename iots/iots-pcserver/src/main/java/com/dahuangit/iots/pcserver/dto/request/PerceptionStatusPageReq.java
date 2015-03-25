package com.dahuangit.iots.pcserver.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

/**
 * 跳转到设备状态请求类
 * 
 * @author 大黄
 * 
 *         2015年3月25日下午4:48:24
 */
public class PerceptionStatusPageReq extends OpRequest {
	/** 设备id */
	private Integer perceptionId = null;

	/** 是否为初始状态下的查询 */
	private Boolean isInit = true;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}

}
