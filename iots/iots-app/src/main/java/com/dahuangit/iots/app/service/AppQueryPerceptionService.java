package com.dahuangit.iots.app.service;

import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionRuntimeLogListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionRuntimeLogListResponse;

public interface AppQueryPerceptionService {
	/**
	 * app获取感知端列表
	 * 
	 * @return
	 */
	public AppGetPerceptionListResponse appGetPerceptionList(AppGetPerceptionListRequest request);

	/**
	 * app获取感知端运行日志列表
	 * 
	 * @return
	 */
	public AppGetPerceptionRuntimeLogListResponse appGetPerceptionRuntimeLogList(
			AppGetPerceptionRuntimeLogListRequest request);
}
