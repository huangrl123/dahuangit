package com.dahuangit.iots.perception.service;

import java.util.List;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.opm.request.PerceptionParamOpRequest;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionParamOpResponse;

public interface PerceptionService {

	PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit);

	List<PerceptionParamOpResponse> findPerceptionParams(Integer perceptionId);

	void updatePerceptionParam(PerceptionParamOpRequest perceptionParamOpRequest);
}
