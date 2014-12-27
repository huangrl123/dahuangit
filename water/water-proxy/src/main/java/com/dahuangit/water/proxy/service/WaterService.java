package com.dahuangit.water.proxy.service;

import com.dahuangit.base.dto.Response;
import com.dahuangit.water.proxy.dto.request.JiankongRequest;
import com.dahuangit.water.proxy.dto.request.LoginRequest;
import com.dahuangit.water.proxy.dto.request.ShouzhiRequest;
import com.dahuangit.water.proxy.dto.request.SunyiRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRequest;
import com.dahuangit.water.proxy.dto.response.GetLdListResponse;
import com.dahuangit.water.proxy.dto.response.GetProjectListResponse;
import com.dahuangit.water.proxy.dto.response.JiankongResponse;
import com.dahuangit.water.proxy.dto.response.LoginResponse;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.SunyiResponse;

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
	 * @return
	 * @throws Exception 
	 */
	public GetProjectListResponse getProjectList() throws Exception;

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	public GetLdListResponse getLdList() throws Exception;

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
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public SunyiResponse sunyi(SunyiRequest request) throws Exception;

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public Response yongshui(YongshuiRequest request) throws Exception;
}
