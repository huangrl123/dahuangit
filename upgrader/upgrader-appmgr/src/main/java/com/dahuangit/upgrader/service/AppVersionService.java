package com.dahuangit.upgrader.service;

import com.dahuangit.upgrader.dto.CheckAppNewVersionRequest;
import com.dahuangit.upgrader.dto.CheckAppNewVersionResponse;
import com.dahuangit.upgrader.dto.GetNewAppFileResponse;

public interface AppVersionService {

	/**
	 * 检查app新版本
	 * 
	 * @param request
	 * @return
	 */
	CheckAppNewVersionResponse checkAppNewVersion(CheckAppNewVersionRequest request);

	/**
	 * 获取最新版本的app文件
	 * 
	 * @return
	 */
	GetNewAppFileResponse getNewAppFile();
}
