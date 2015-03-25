package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

/**
 * 添加设备
 * 
 * @author 大黄
 * 
 *         2015年3月25日下午4:03:57
 */
public class AddPerceptionReq extends OpRequest {
	/** 设备类型id */
	private Integer perceptionTypeId = null;

	/** 设备地址 */
	private String percetionAddr = null;

	/** 设备名称 */
	private String perceptionName = null;

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPercetionAddr() {
		return percetionAddr;
	}

	public void setPercetionAddr(String percetionAddr) {
		this.percetionAddr = percetionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

}
