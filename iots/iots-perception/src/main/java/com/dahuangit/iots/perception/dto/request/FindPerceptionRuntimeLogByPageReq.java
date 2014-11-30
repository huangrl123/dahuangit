package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

public class FindPerceptionRuntimeLogByPageReq extends OpRequest {

	private Integer perceptionId = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

}
