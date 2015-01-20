package com.dahuangit.water.proxy.service;

import com.dahuangit.base.dto.Response;
import com.dahuangit.water.proxy.dto.request.JiankongRequest;
import com.dahuangit.water.proxy.dto.request.LoginRequest;
import com.dahuangit.water.proxy.dto.request.ShouzhiRequest;
import com.dahuangit.water.proxy.dto.request.SubmitYongshuiRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRecordRequest;
import com.dahuangit.water.proxy.dto.request.YujingRequest;
import com.dahuangit.water.proxy.dto.response.GetLdListResponse;
import com.dahuangit.water.proxy.dto.response.GetProjectListResponse;
import com.dahuangit.water.proxy.dto.response.JiankongResponse;
import com.dahuangit.water.proxy.dto.response.LoginResponse;
import com.dahuangit.water.proxy.dto.response.RecentYearSemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterSumResponse;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.YongshuiRecordResponse;
import com.dahuangit.water.proxy.dto.response.YujingResponse;

public interface WaterService {
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public LoginResponse login(LoginRequest request) throws Exception;

	/**
	 * 获取项目列表
	 * 
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public GetProjectListResponse getProjectList(String systemId) throws Exception;

	/**
	 * 获取楼栋列表
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public GetLdListResponse getLdList(String systemId, String projectId) throws Exception;

	/**
	 * 获取收资情况
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ShouzhiResponse shouzhi(ShouzhiRequest request) throws Exception;

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JiankongResponse jiankong(JiankongRequest request) throws Exception;

	/**
	 * 获取预警信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public YujingResponse yujing(YujingRequest request) throws Exception;

	/**
	 * 获取学期概况
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public SemesterSumResponse getSemesterSum(String systemId, String projectId) throws Exception;

	/**
	 * 学期月份收益
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public SemesterMonthResponse getSemesterMonth(String systemId, String projectId) throws Exception;

	/**
	 * 近三年学期收益
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public RecentYearSemesterMonthResponse getRecentYearSemesterMonth(String systemId, String projectId)
			throws Exception;

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response yongshui(SubmitYongshuiRequest request) throws Exception;

	/**
	 * 用水记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public YongshuiRecordResponse yongshuiRecord(YongshuiRecordRequest request) throws Exception;
}
