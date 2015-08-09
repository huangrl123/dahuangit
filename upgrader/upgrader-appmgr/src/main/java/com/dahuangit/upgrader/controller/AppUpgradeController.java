package com.dahuangit.upgrader.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.upgrader.dto.AddDownloadRecordRequest;
import com.dahuangit.upgrader.dto.AddDownloadRecordResponse;
import com.dahuangit.upgrader.dto.CheckAppNewVersionRequest;
import com.dahuangit.upgrader.dto.CheckAppNewVersionResponse;
import com.dahuangit.upgrader.dto.GetNewAppFileResponse;
import com.dahuangit.upgrader.service.AppVersionService;
import com.dahuangit.upgrader.service.DownloadRecordService;

/**
 * app更新控制器
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午11:53:17
 */
@Controller
@RequestMapping("/appUpgrade")
public class AppUpgradeController extends BaseController {

	@Autowired
	private AppVersionService appVersionService = null;

	@Autowired
	private DownloadRecordService downloadRecordService = null;

	/**
	 * 检查app新版本
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkAppNewVersion", method = RequestMethod.POST)
	@ResponseBody
	public CheckAppNewVersionResponse checkAppNewVersion(CheckAppNewVersionRequest request) {
		CheckAppNewVersionResponse response = new CheckAppNewVersionResponse();

		try {
			response = this.appVersionService.checkAppNewVersion(request);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return response;
	}

	/**
	 * 跳转到下载界面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/toDownloadAppPage", method = RequestMethod.GET)
	public String toDownloadAppPage(ModelMap map) {
		
		GetNewAppFileResponse response = this.appVersionService.getNewAppFile();
		
		map.put("response", response);
		
		return "/mobile/downloadApp";
	}

	/**
	 * 添加下载记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addDownloadRecord", method = RequestMethod.POST)
	@ResponseBody
	public AddDownloadRecordResponse addDownloadRecord(HttpServletRequest httpServletRequest) {
		AddDownloadRecordResponse response = new AddDownloadRecordResponse();

		try {
			AddDownloadRecordRequest request = new AddDownloadRecordRequest();
			String clientAddr = httpServletRequest.getRemoteHost();
			request.setClientAddr(clientAddr);
			this.downloadRecordService.addDownloadRecord(request);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		}

		return response;
	}
}
