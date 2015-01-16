package com.dahuangit.water.proxy.dto.response;

import com.dahuangit.base.dto.Response;

/**
 * 学期概况,当请求参数中请求动作类型为1时返回
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:29:09
 */
public class SemesterSumResponse extends Response {

	private SemesterSumInfo semesterSumInfo = new SemesterSumInfo();

	public SemesterSumInfo getSemesterSumInfo() {
		return semesterSumInfo;
	}

	public void setSemesterSumInfo(SemesterSumInfo semesterSumInfo) {
		this.semesterSumInfo = semesterSumInfo;
	}

}
