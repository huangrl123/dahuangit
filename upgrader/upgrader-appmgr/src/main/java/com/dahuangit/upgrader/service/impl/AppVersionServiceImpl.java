package com.dahuangit.upgrader.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.upgrader.dto.CheckAppNewVersionRequest;
import com.dahuangit.upgrader.dto.CheckAppNewVersionResponse;
import com.dahuangit.upgrader.dto.GetNewAppFileResponse;
import com.dahuangit.upgrader.service.AppVersionService;

/**
 * app版本服务实现类
 * 
 * @author 黄仁良
 *
 *         2015年8月9日上午12:08:44
 */
@Component
@Transactional
public class AppVersionServiceImpl implements AppVersionService {

	@Value("${appfile.dir}")
	private String appfileDir = null;

	@Value("${local.server.ip}")
	private String localIp = null;

	@Value("${local.server.port}")
	private Integer localPort = null;

	@Value("${local.server.context}")
	private String serverContext = null;

	/**
	 * 检查app新版本
	 * 
	 * @param request
	 * @return
	 */
	public CheckAppNewVersionResponse checkAppNewVersion(CheckAppNewVersionRequest request) {
		CheckAppNewVersionResponse response = new CheckAppNewVersionResponse();

		File file = new File(appfileDir);

		File[] arr = file.listFiles();

		String appFileName = arr[1].getName();
		String version = appFileName.substring(appFileName.indexOf(".") - 1, appFileName.lastIndexOf("."));

		response.setVersion(version);

		String appUrl = "http://" + this.localIp + ":" + this.localPort + this.serverContext + "/appfile/"
				+ appFileName;
		
		response.setAppUrl(appUrl);

		return response;
	}

	/**
	 * 获取最新版本的app文件
	 * 
	 * @return
	 */
	public GetNewAppFileResponse getNewAppFile() {
		File file = new File(appfileDir);

		File[] arr = file.listFiles();

		String appFileName = arr[1].getName();

		String appUrl = "http://" + this.localIp + ":" + this.localPort + "/" + this.serverContext + "/appfile/"
				+ appFileName;

		GetNewAppFileResponse response = new GetNewAppFileResponse();
		response.setAppName(appFileName);
		response.setAppFileUrl(appUrl);

		return response;
	}
}
