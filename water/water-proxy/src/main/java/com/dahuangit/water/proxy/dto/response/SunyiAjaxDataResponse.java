package com.dahuangit.water.proxy.dto.response;

import com.dahuangit.base.dto.Response;

public class SunyiAjaxDataResponse extends Response {
	private ChartInfoZhutu semesterMonthChartInfo = null;

	private ChartInfo recentYearSemesterMonthChartInfo = null;

	public ChartInfoZhutu getSemesterMonthChartInfo() {
		return semesterMonthChartInfo;
	}

	public void setSemesterMonthChartInfo(ChartInfoZhutu semesterMonthChartInfo) {
		this.semesterMonthChartInfo = semesterMonthChartInfo;
	}

	public ChartInfo getRecentYearSemesterMonthChartInfo() {
		return recentYearSemesterMonthChartInfo;
	}

	public void setRecentYearSemesterMonthChartInfo(ChartInfo recentYearSemesterMonthChartInfo) {
		this.recentYearSemesterMonthChartInfo = recentYearSemesterMonthChartInfo;
	}

}
