package com.dahuang.water.proxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
import com.dahuang.water.proxy.service.WaterService;
import com.dahuang.water.proxy.util.ConnectWaterSysUtils;
import com.dahuangit.base.dto.Response;

@Component
@Transactional
public class WaterServiceImpl implements WaterService {

	@Value("${water.system.server.url.Login}")
	private String login_url = null;

	@Value("${water.system.server.url.GetProjectList}")
	private String getProjectList_url = null;

	@Value("${water.system.server.url.GetLdList}")
	private String getLdList_url = null;

	@Value("${water.system.server.url.Shouzhi}")
	private String shouzhi_url = null;

	@Value("${water.system.server.url.Jiankong}")
	private String jiankong_url = null;

	@Value("${water.system.server.url.Sunyi}")
	private String sunyi_url = null;

	@Value("${water.system.server.url.Yongshui}")
	private String yongshui_url = null;

	@Autowired
	protected CastorMarshaller xmlMarshaller = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	public LoginResponse login(LoginRequest request) {

		LoginResponse response = (LoginResponse) ConnectWaterSysUtils.connect(request, login_url, xmlMarshaller,
				LoginResponse.class);

		return response;
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	public GetProjectListResponse getProjectList() {

		GetProjectListResponse response = (GetProjectListResponse) ConnectWaterSysUtils.connect(null,
				getProjectList_url, xmlMarshaller, GetProjectListResponse.class);

		return response;
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 */
	public GetLdListResponse getLdList() {

		GetLdListResponse response = (GetLdListResponse) ConnectWaterSysUtils.connect(null, getLdList_url,
				xmlMarshaller, GetLdListResponse.class);

		return response;
	}

	/**
	 * 获取收资情况
	 * 
	 * @param request
	 * @return
	 */
	public ShouzhiResponse shouzhi(ShouzhiRequest request) {

		ShouzhiResponse response = (ShouzhiResponse) ConnectWaterSysUtils.connect(request, shouzhi_url, xmlMarshaller,
				ShouzhiResponse.class);

		return response;
	}

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 */
	public JiankongResponse jiankong(JiankongRequest request) {

		JiankongResponse response = (JiankongResponse) ConnectWaterSysUtils.connect(request, jiankong_url,
				xmlMarshaller, JiankongResponse.class);

		return response;
	}

	/**
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 */
	public SunyiResponse sunyi(SunyiRequest request) {

		SunyiResponse response = (SunyiResponse) ConnectWaterSysUtils.connect(request, sunyi_url, xmlMarshaller,
				SunyiResponse.class);

		return response;
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	public Response yongshui(YongshuiRequest request) {

		Response response = (Response) ConnectWaterSysUtils.connect(request, yongshui_url, xmlMarshaller,
				Response.class);

		return response;
	}

}
