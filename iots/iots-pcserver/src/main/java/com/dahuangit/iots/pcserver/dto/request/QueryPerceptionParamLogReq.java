package com.dahuangit.iots.pcserver.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

public class QueryPerceptionParamLogReq extends OpRequest {
	private Integer perceptionId = null;

	/** 状态参数id */
	private Integer perceptionParamId = null;

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

}
