package com.dahuangit.water.proxy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.Response;
import com.dahuangit.water.proxy.dto.request.JiankongRequest;
import com.dahuangit.water.proxy.dto.request.LoginRequest;
import com.dahuangit.water.proxy.dto.request.ShouzhiRequest;
import com.dahuangit.water.proxy.dto.request.SunyiRequest;
import com.dahuangit.water.proxy.dto.request.YongshuiRequest;
import com.dahuangit.water.proxy.dto.response.GetLdListResponse;
import com.dahuangit.water.proxy.dto.response.GetProjectListResponse;
import com.dahuangit.water.proxy.dto.response.JiankongInfo;
import com.dahuangit.water.proxy.dto.response.JiankongResponse;
import com.dahuangit.water.proxy.dto.response.LdInfo;
import com.dahuangit.water.proxy.dto.response.LoginResponse;
import com.dahuangit.water.proxy.dto.response.PowerInfo;
import com.dahuangit.water.proxy.dto.response.ProjectInfo;
import com.dahuangit.water.proxy.dto.response.RecentYearSemesterMonthInfo;
import com.dahuangit.water.proxy.dto.response.SemesterMonthInfo;
import com.dahuangit.water.proxy.dto.response.SemesterSumInfo;
import com.dahuangit.water.proxy.dto.response.ShouzhiInfo;
import com.dahuangit.water.proxy.dto.response.ShouzhiResponse;
import com.dahuangit.water.proxy.dto.response.SunyiResponse;
import com.dahuangit.water.proxy.service.WaterService;

@Controller
@RequestMapping("/waterSys")
public class WaterSysMockController extends BaseController {

	@Autowired
	private WaterService waterService = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		List<PowerInfo> powerInfos = new ArrayList<PowerInfo>();

		PowerInfo power1 = new PowerInfo();
		power1.setPowerId("1");
		power1.setPowerName("抄表员");
		powerInfos.add(power1);

		PowerInfo power2 = new PowerInfo();
		power2.setPowerId("2");
		power2.setPowerName("系统管理员");
		powerInfos.add(power2);

		response.setPowerInfos(powerInfos);
		return this.responseToXml(response);
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectList() {
		GetProjectListResponse response = new GetProjectListResponse();
		List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();

		for (int i = 0; i < 10; i++) {
			ProjectInfo p = new ProjectInfo();
			p.setProjectId(String.valueOf(i));
			p.setProjectName("项目" + i);
			projectInfos.add(p);
		}

		response.setProjectInfos(projectInfos);

		return this.responseToXml(response);
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLdList", method = RequestMethod.POST)
	@ResponseBody
	public String getLdList() {
		GetLdListResponse response = new GetLdListResponse();
		List<LdInfo> ldInfos = new ArrayList<LdInfo>();

		for (int i = 0; i < 10; i++) {
			LdInfo ld = new LdInfo();
			ld.setLdId(String.valueOf(i));
			ld.setLdName("楼栋" + i);
			ldInfos.add(ld);
		}

		response.setLdInfos(ldInfos);

		return this.responseToXml(response);
	}

	/**
	 * 获取收资情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shouzhi", method = RequestMethod.POST)
	@ResponseBody
	public String shouzhi(ShouzhiRequest request) {
		ShouzhiResponse response = new ShouzhiResponse();
		List<ShouzhiInfo> shouzhiInfos = new ArrayList<ShouzhiInfo>();

		for (int i = 0; i < 10; i++) {
			ShouzhiInfo shouzhi = new ShouzhiInfo();
			shouzhi.setProjectName("项目" + i);
			shouzhi.setOperatorName("张三");
			shouzhi.setSumMoney("55455.33");
		}

		response.setShouzhiInfos(shouzhiInfos);

		return this.responseToXml(response);
	}

	/**
	 * 获取监控情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jiankong", method = RequestMethod.POST)
	@ResponseBody
	public String jiankong(JiankongRequest request) {
		JiankongResponse response = new JiankongResponse();
		List<JiankongInfo> jiankongInfos = new ArrayList<JiankongInfo>();
		for (int i = 0; i < 10; i++) {
			JiankongInfo jiankong = new JiankongInfo();
			jiankong.setProjectName("项目" + 1);
			jiankong.setDeviceAddr("43543645546");
			jiankong.setSumYd("445");
			jiankong.setSumYs("655");
		}

		response.setJiankongInfos(jiankongInfos);
		return this.responseToXml(response);
	}

	/**
	 * 获取损益情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sunyi", method = RequestMethod.POST)
	@ResponseBody
	public String sunyi(SunyiRequest request) {
		SunyiResponse response = new SunyiResponse();
		SemesterSumInfo semesterSumInfo = new SemesterSumInfo();
		semesterSumInfo.setProjectName("项目1");
		semesterSumInfo.setSumCb("43");
		semesterSumInfo.setSumInout("54");
		semesterSumInfo.setSumSy("7");
		semesterSumInfo.setSumXf("87");
		semesterSumInfo.setSumZs("98");
		response.setSemesterSumInfo(semesterSumInfo);

		List<SemesterMonthInfo> semesterMonthInfos = new ArrayList<SemesterMonthInfo>();
		for (int i = 0; i < 10; i++) {
			SemesterMonthInfo info = new SemesterMonthInfo();
			info.setProjectName("项目" + i);
			info.setSumSy("3432" + i);
			info.setyMonth("2014-" + i);
			semesterMonthInfos.add(info);
		}
		response.setSemesterMonthInfos(semesterMonthInfos);

		List<RecentYearSemesterMonthInfo> recentYearSemesterMonthInfos = new ArrayList<RecentYearSemesterMonthInfo>();
		for (int i = 0; i < 10; i++) {
			RecentYearSemesterMonthInfo info = new RecentYearSemesterMonthInfo();
			info.setProjectName("项目" + i);
			info.setSumSy("323" + i);
			info.setyMonth("2013-" + i);
		}
		response.setRecentYearSemesterMonthInfos(recentYearSemesterMonthInfos);

		return this.responseToXml(response);
	}

	/**
	 * 用水登记
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yongshui", method = RequestMethod.POST)
	@ResponseBody
	public String yongshui(YongshuiRequest request) {
		Response response = new Response();

		return this.responseToXml(response);
	}
}
