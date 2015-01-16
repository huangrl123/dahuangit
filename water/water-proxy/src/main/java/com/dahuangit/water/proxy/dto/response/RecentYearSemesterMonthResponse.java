package com.dahuangit.water.proxy.dto.response;

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
public class RecentYearSemesterMonthResponse extends Response {

	private List<RecentYearSemesterMonthInfo> recentYearSemesterMonthInfos = new ArrayList<RecentYearSemesterMonthInfo>();

	public List<RecentYearSemesterMonthInfo> getRecentYearSemesterMonthInfos() {
		return recentYearSemesterMonthInfos;
	}

	public void setRecentYearSemesterMonthInfos(List<RecentYearSemesterMonthInfo> recentYearSemesterMonthInfos) {
		this.recentYearSemesterMonthInfos = recentYearSemesterMonthInfos;
	}

}
