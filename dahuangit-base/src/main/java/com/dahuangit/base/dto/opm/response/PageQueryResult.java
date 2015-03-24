package com.dahuangit.base.dto.opm.response;

import java.util.ArrayList;
import java.util.List;

public class PageQueryResult<T> {

	private long total;

	private List<T> rows = new ArrayList<T>();

	public PageQueryResult() {
	}

	public PageQueryResult(List<T> results, long totalCount) {
		this.rows = results;
		this.total = totalCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
