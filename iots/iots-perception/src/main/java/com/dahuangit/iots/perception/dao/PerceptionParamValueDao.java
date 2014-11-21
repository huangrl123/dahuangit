package com.dahuangit.iots.perception.dao;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.entry.PerceptionParamValueInfo;

@Component
public class PerceptionParamValueDao extends BaseDao<PerceptionParamValueInfo, Integer> {

	public Integer getPerceptionParamValueInfoId(Integer perceptionTypeId, Integer perceptionParamId,
			Integer perceptionParamValue) {
		String hql = "select perceptionParamValueInfoId from PerceptionParamValueInfo p where p.perceptionTypeId=? and p.perceptionParamId=? and p.perceptionParamValue=?";
		Integer value = this.findUnique(hql, perceptionTypeId, perceptionParamId, perceptionParamValue);
		return value;
	}

	public Integer getPerceptionParamValue(Integer perceptionTypeId, Integer perceptionParamId,
			Integer perceptionParamValueId) {
		String hql = "select p.perceptionParamValue from PerceptionParamValueInfo p where p.perceptionTypeId=? and p.perceptionParamId=? and p.perceptionParamValueInfoId=?";
		Integer value = this.findUnique(hql, perceptionTypeId, perceptionParamId, perceptionParamValueId);
		return value;
	}

}
