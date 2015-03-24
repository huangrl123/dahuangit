package com.dahuangit.base.dto.opm.request;

import com.dahuangit.base.dto.Request;

public class OpRequest extends Request {
	/** 起始记录下标 */
	private Integer start = 0;

	/** 获取记录数 */
	private Integer limit = 25;

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

}
