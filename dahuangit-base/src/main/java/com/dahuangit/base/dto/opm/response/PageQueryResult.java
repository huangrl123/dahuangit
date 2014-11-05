package com.dahuangit.base.dto.opm.response;

import java.util.ArrayList;
import java.util.List;

public class PageQueryResult<T> {

	private long totalCount;

	private List<T> results = new ArrayList<T>();

	public PageQueryResult() {
	}

	public PageQueryResult(List<T> results, long totalCount) {
		this.results = results;
		this.totalCount = totalCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> rows) {
		this.results = rows;
	}

}
