package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

public class FindPerceptionRuntimeLogByPageReq extends OpRequest {

	/** 起始记录下标 */
	private Integer start = 0;

	/** 获取记录数 */
	private Integer limit = 25;

	private Integer perceptionId = null;
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

}
