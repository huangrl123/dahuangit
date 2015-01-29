package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

public class RemoteCtrlPerceptionRequest extends Request {
	/** 设备id */
	private Integer perceptionId = null;

	/** 设备类型id */
	private Integer perceptionTypeId = null;

	/** 参数id */
	private Integer paramId = null;

	/** 参数值 */
	private Integer paramValue = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public Integer getParamValue() {
		return paramValue;
	}

	public void setParamValue(Integer paramValue) {
		this.paramValue = paramValue;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

}
