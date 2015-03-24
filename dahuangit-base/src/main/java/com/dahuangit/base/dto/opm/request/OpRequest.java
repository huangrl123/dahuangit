package com.dahuangit.base.dto.opm.request;

import com.dahuangit.base.dto.Request;

public class OpRequest extends Request {
	/** 起始记录下标 */
	private Integer start = 0;

	/** 获取记录数 */
	private Integer limit = 25;

	private Integer page = null;

	private Integer rows = null;

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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

}
