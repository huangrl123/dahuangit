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
public class SemesterMonthResponse extends Response {

	private List<SemesterMonthInfo> semesterMonthInfos = new ArrayList<SemesterMonthInfo>();

	public List<SemesterMonthInfo> getSemesterMonthInfos() {
		return semesterMonthInfos;
	}

	public void setSemesterMonthInfos(List<SemesterMonthInfo> semesterMonthInfos) {
		this.semesterMonthInfos = semesterMonthInfos;
	}

}
