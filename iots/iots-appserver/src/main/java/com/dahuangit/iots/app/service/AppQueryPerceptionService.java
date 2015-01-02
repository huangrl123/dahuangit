package com.dahuangit.iots.app.service;

import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;

public interface AppQueryPerceptionService {
	/**
	 * app获取感知端列表
	 * 
	 * @return
	 */
	public AppGetPerceptionListResponse appGetPerceptionList(AppGetPerceptionListRequest request);
}
