package com.dahuangit.water.proxy.service.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
import com.dahuangit.water.proxy.dto.response.PageBean;
import com.dahuangit.water.proxy.dto.response.RecentYearSemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterMonthResponse;
import com.dahuangit.water.proxy.dto.response.SemesterSumResponse;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.YongshuiRecordResponse;
import com.dahuangit.water.proxy.dto.response.YujingResponse;
import com.dahuangit.water.proxy.service.WaterService;
import com.dahuangit.water.proxy.util.ConnectWaterSysUtils;

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

	@Value("${water.system.server.url.Yujing}")
	private String yujing_url = null;

	@Value("${water.system.server.url.Jiankong}")
	private String jiankong_url = null;

	@Value("${water.system.server.url.Sunyi}")
	private String sunyi_url = null;

	@Value("${water.system.server.url.Yongshui}")
	private String yongshui_url = null;

	@Value("${water.system.server.url.YongshuiRecord}")
	private String yongshuiRecord_url = null;

	@Autowired
	protected CastorMarshaller xmlMarshaller = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public LoginResponse login(LoginRequest request) throws Exception {

		String url = MessageFormat.format(login_url, request.getOptNum(), request.getPassword());
		LoginResponse response = (LoginResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller, LoginResponse.class);

		return response;
	}

	/**
	 * 获取项目列表
	 * 
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public GetProjectListResponse getProjectList(String systemId) throws Exception {

		String url = MessageFormat.format(getProjectList_url, systemId);

		GetProjectListResponse response = (GetProjectListResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				GetProjectListResponse.class);

		return response;
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public GetLdListResponse getLdList(String systemId, String projectId) throws Exception {

		String url = MessageFormat.format(getLdList_url, systemId, projectId);

		GetLdListResponse response = (GetLdListResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				GetLdListResponse.class);

		return response;
	}

	/**
	 * 获取收支情况
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ShouzhiResponse shouzhi(ShouzhiRequest request) throws Exception {

		String url = MessageFormat.format(shouzhi_url, request.getSystemId(), request.getProjectId(),
				request.getBeginTime(), request.getEndTime());

		ShouzhiResponse response = (ShouzhiResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				ShouzhiResponse.class);

		return response;
	}

	/**
	 * 获取预警信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public YujingResponse yujing(YujingRequest request) throws Exception {
		String url = MessageFormat.format(yujing_url, request.getSystemId(), request.getProjectId(),
				request.getBeginTime(), request.getEndTime(), request.getReqPage());

		YujingResponse response = (YujingResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				YujingResponse.class);

		PageBean pageBean = new PageBean(request.getReqPage(), response.getTotalPageCount());
		response.setPageBean(pageBean);
		
		return response;
	}

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JiankongResponse jiankong(JiankongRequest request) throws Exception {

		JiankongResponse response = (JiankongResponse) ConnectWaterSysUtils.connect(jiankong_url, xmlMarshaller,
				JiankongResponse.class);

		return response;
	}

	/**
	 * 获取学期概况
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public SemesterSumResponse getSemesterSum(String systemId, String projectId) throws Exception {

		String url = MessageFormat.format(sunyi_url, systemId, projectId, "1");
		SemesterSumResponse response = (SemesterSumResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				SemesterSumResponse.class);

		return response;
	}

	/**
	 * 学期月份收益
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public SemesterMonthResponse getSemesterMonth(String systemId, String projectId) throws Exception {
		String url = MessageFormat.format(sunyi_url, systemId, projectId, "2");
		SemesterMonthResponse response = (SemesterMonthResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				SemesterMonthResponse.class);

		return response;
	}

	/**
	 * 近三年学期收益
	 * 
	 * @param systemId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public RecentYearSemesterMonthResponse getRecentYearSemesterMonth(String systemId, String projectId)
			throws Exception {

		String url = MessageFormat.format(sunyi_url, systemId, projectId, "3");

		RecentYearSemesterMonthResponse response = (RecentYearSemesterMonthResponse) ConnectWaterSysUtils.connect(url,
				xmlMarshaller, RecentYearSemesterMonthResponse.class);

		return response;
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response yongshui(SubmitYongshuiRequest request) throws Exception {

		String url = MessageFormat.format(yongshui_url, request.getSystemId(), request.getProjectId(),
				request.getLdId(), request.getYongshuiSum(), request.getYongdianSum());

		Response response = (Response) ConnectWaterSysUtils.connect(url, xmlMarshaller, Response.class);

		return response;
	}

	/**
	 * 用水记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public YongshuiRecordResponse yongshuiRecord(YongshuiRecordRequest request) throws Exception {
		String url = MessageFormat.format(yongshuiRecord_url, request.getSystemId(), request.getProjectId(),
				request.getLdId());

		YongshuiRecordResponse response = (YongshuiRecordResponse) ConnectWaterSysUtils.connect(url, xmlMarshaller,
				YongshuiRecordResponse.class);

		return response;
	}
}
