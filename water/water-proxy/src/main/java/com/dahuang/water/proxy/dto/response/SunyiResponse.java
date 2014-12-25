package com.dahuang.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 损益响应
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:41:50
 */
public class SunyiResponse extends Response {

	private SemesterSumInfo semesterSumInfo = new SemesterSumInfo();

	private List<SemesterMonthInfo> semesterMonthInfos = new ArrayList<SemesterMonthInfo>();

	private List<RecentYearSemesterMonthInfo> recentYearSemesterMonthInfos = new ArrayList<RecentYearSemesterMonthInfo>();

	public SemesterSumInfo getSemesterSumInfo() {
		return semesterSumInfo;
	}

	public void setSemesterSumInfo(SemesterSumInfo semesterSumInfo) {
		this.semesterSumInfo = semesterSumInfo;
	}

	public List<SemesterMonthInfo> getSemesterMonthInfos() {
		return semesterMonthInfos;
	}

	public void setSemesterMonthInfos(List<SemesterMonthInfo> semesterMonthInfos) {
		this.semesterMonthInfos = semesterMonthInfos;
	}

	public List<RecentYearSemesterMonthInfo> getRecentYearSemesterMonthInfos() {
		return recentYearSemesterMonthInfos;
	}

	public void setRecentYearSemesterMonthInfos(List<RecentYearSemesterMonthInfo> recentYearSemesterMonthInfos) {
		this.recentYearSemesterMonthInfos = recentYearSemesterMonthInfos;
	}

}
