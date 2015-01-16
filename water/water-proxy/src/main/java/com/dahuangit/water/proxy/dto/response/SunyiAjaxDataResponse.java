package com.dahuangit.water.proxy.dto.response;

import com.dahuangit.base.dto.Response;

public class SunyiAjaxDataResponse extends Response {
	private ChartInfo semesterMonthChartInfo = null;

	private ChartInfo recentYearSemesterMonthChartInfo = null;

	public ChartInfo getSemesterMonthChartInfo() {
		return semesterMonthChartInfo;
	}

	public void setSemesterMonthChartInfo(ChartInfo semesterMonthChartInfo) {
		this.semesterMonthChartInfo = semesterMonthChartInfo;
	}

	public ChartInfo getRecentYearSemesterMonthChartInfo() {
		return recentYearSemesterMonthChartInfo;
	}

	public void setRecentYearSemesterMonthChartInfo(ChartInfo recentYearSemesterMonthChartInfo) {
		this.recentYearSemesterMonthChartInfo = recentYearSemesterMonthChartInfo;
	}

}
