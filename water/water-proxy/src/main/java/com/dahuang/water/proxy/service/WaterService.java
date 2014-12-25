package com.dahuang.water.proxy.service;

import com.dahuang.water.proxy.dto.request.JiankongRequest;
import com.dahuang.water.proxy.dto.request.LoginRequest;
import com.dahuang.water.proxy.dto.request.ShouzhiRequest;
import com.dahuang.water.proxy.dto.request.SunyiRequest;
import com.dahuang.water.proxy.dto.request.YongshuiRequest;
import com.dahuang.water.proxy.dto.response.GetLdListResponse;
import com.dahuang.water.proxy.dto.response.GetProjectListResponse;
import com.dahuang.water.proxy.dto.response.JiankongResponse;
import com.dahuang.water.proxy.dto.response.LoginResponse;
import com.dahuang.water.proxy.dto.response.ShouzhiResponse;
import com.dahuang.water.proxy.dto.response.SunyiResponse;
import com.dahuangit.base.dto.Response;

public interface WaterService {
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	public LoginResponse login(LoginRequest request);

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	public GetProjectListResponse getProjectList();

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 */
	public GetLdListResponse getLdList();

	/**
	 * 获取收资情况
	 * 
	 * @param request
	 * @return
	 */
	public ShouzhiResponse shouzhi(ShouzhiRequest request);

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 */
	public JiankongResponse jiankong(JiankongRequest request);

	/**
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 */
	public SunyiResponse sunyi(SunyiRequest request);

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	public Response yongshui(YongshuiRequest request);
}
