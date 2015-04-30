package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

/**
 * 保存设备(包括添加和修改)
 * 
 * @author 大黄
 * 
 *         2015年3月25日下午4:03:57
 */
public class SavePerceptionReq extends OpRequest {

	private Integer perceptionId = null;

	/** 设备类型id */
	private Integer perceptionTypeId = null;

	/** 设备地址 */
	private String perceptionAddr = null;

	/** 设备名称 */
	private String perceptionName = null;

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

}
