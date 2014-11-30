package com.dahuangit.iots.perception.service;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.request.PerceptionVediaFileUploadNoticeRequest;
import com.dahuangit.iots.perception.dto.response.PercetionVediaFileResponse;

public interface PerceptionVediaService {

	public PageQueryResult<PercetionVediaFileResponse> findPerceptionByPage(Integer perceptionId, Integer start,
			Integer limit);

	public void fileUploadNotice(PerceptionVediaFileUploadNoticeRequest req) throws Exception;
}
