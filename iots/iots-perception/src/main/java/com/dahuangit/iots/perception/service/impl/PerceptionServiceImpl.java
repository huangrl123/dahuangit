package com.dahuangit.iots.perception.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionParamDao;
import com.dahuangit.iots.perception.dto.opm.request.PerceptionParamOpRequest;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionParamOpResponse;
import com.dahuangit.iots.perception.dto.oxm.response.RemoteCtrlMachineResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionParam;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.service.RemoteCtrlService;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.util.bean.dto.DtoBuilder;

@Component
@Transactional
public class PerceptionServiceImpl implements PerceptionService {

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private PerceptionParamDao perceptionParamDao = null;

	@Autowired
	private RemoteCtrlService remoteCtrlService = null;

	@Autowired
	private RemoteQueryService remoteQueryService = null;

	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit) {
		PageQueryResult<PerceptionOpResponse> pageQueryResult = new PageQueryResult<PerceptionOpResponse>();

		List<PerceptionOpResponse> rows = new ArrayList<PerceptionOpResponse>();
		List<Perception> list = this.perceptionDao.findPerceptionByPage(start, limit);

		for (Perception p : list) {
			PerceptionOpResponse por = DtoBuilder.buildDto(PerceptionOpResponse.class, p);
			rows.add(por);
		}

		Long totalCount = this.perceptionDao.findPerceptionCount();

		pageQueryResult.setResults(rows);
		pageQueryResult.setTotalCount(totalCount);

		return pageQueryResult;
	}

	public List<PerceptionParamOpResponse> findPerceptionParams(Integer perceptionId) {
		List<PerceptionParam> perceptionParams = this.perceptionParamDao.findBy("pid", perceptionId);

		List<PerceptionParamOpResponse> list = new ArrayList<PerceptionParamOpResponse>();
		for (PerceptionParam p : perceptionParams) {
			PerceptionParamOpResponse pp = DtoBuilder.buildDto(PerceptionParamOpResponse.class, p);
			list.add(pp);
		}

		return list;
	}

	public void updatePerceptionParam(PerceptionParamOpRequest perceptionParamOpRequest) {
		// 进行远程控制，如果成功，则保存数据库
		RemoteCtrlMachineResponse response = this.remoteCtrlService.doRemoteCtrl(
				perceptionParamOpRequest.getMachineAddr(), perceptionParamOpRequest.getParamValue());

		if (!response.isSuccess()) {
			new RuntimeException("远程控制失败,原因:" + response.getMsg());
		}

		PerceptionParam perceptionParam = this.perceptionParamDao.findPerceptionParamBy(
				perceptionParamOpRequest.getpId(), perceptionParamOpRequest.getParamKey());

		if (perceptionParam == null) {
			perceptionParam = new PerceptionParam();
			perceptionParam.setPid(perceptionParamOpRequest.getpId());
			perceptionParam.setParamKey(perceptionParamOpRequest.getParamKey());
			perceptionParam.setParamValue(perceptionParamOpRequest.getParamValue());
			perceptionParam.setCreateDateTime(new Date());
			perceptionParam.setLastModifyDateTime(new Date());
			this.perceptionParamDao.add(perceptionParam);
		} else {
			perceptionParam.setPid(perceptionParamOpRequest.getpId());
			perceptionParam.setParamValue(perceptionParamOpRequest.getParamValue());
			perceptionParam.setLastModifyDateTime(new Date());
			this.perceptionParamDao.update(perceptionParam);
		}
	}
}
