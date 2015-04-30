package com.dahuangit.iots.perception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuangit.iots.perception.dao.PerceptionParamDao;
import com.dahuangit.iots.perception.entry.PerceptionParam;
import com.dahuangit.iots.perception.service.PerceptionParamService;

@Component
public class PerceptionParamServiceImpl implements PerceptionParamService{

	@Autowired
	private PerceptionParamDao perceptionParamDao = null;

	public PerceptionParam getPerceptionParam(Integer paramId) {
		return this.perceptionParamDao.get(PerceptionParam.class, paramId);
	}
}
