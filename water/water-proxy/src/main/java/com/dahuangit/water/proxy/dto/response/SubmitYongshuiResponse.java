package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

public class SubmitYongshuiResponse extends Response {
	private List<YongshuiRecord> yongshuiRecords = new ArrayList<YongshuiRecord>();

	public List<YongshuiRecord> getYongshuiRecords() {
		return yongshuiRecords;
	}

	public void setYongshuiRecords(List<YongshuiRecord> yongshuiRecords) {
		this.yongshuiRecords = yongshuiRecords;
	}
}
