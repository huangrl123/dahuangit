package com.dahuangit.iots.perception.dao;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.entry.PerceptionParamValueInfo;

@Component
public class PerceptionParamValueDao extends BaseDao<PerceptionParamValueInfo, Integer> {

	public Integer getPerceptionParamValueInfoId(Integer perceptionParamId, Integer perceptionParamValue) {
		String hql = "select perceptionParamValueInfoId from PerceptionParamValueInfo p where p.perceptionParamId=? and p.perceptionParamValue=?";
		Integer value = this.findUnique(hql, perceptionParamId, perceptionParamValue);
		return value;
	}

	public boolean existsPerceptionParamValue(Integer perceptionParamId, Integer perceptionParamValue) {
		String hql = "select p.perceptionParamValueInfoId from PerceptionParamValueInfo p where p.perceptionParamId=? and p.perceptionParamValue=?";
		Integer pk = this.findUnique(hql, perceptionParamId, perceptionParamValue);

		if (null == pk) {
			return false;
		}

		return true;
	}

	public String getPerceptionParamValueDesc(Integer perceptionTypeId, Integer perceptionParamId,
			Integer perceptionParamValue) {
		String hql = "select p.perceptionParamValueDesc from PerceptionParamValueInfo p where p.perceptionTypeId=? and p.perceptionParamId=? and p.perceptionParamValue=?";
		String desc = this.findUnique(hql, perceptionTypeId, perceptionParamId, perceptionParamValue);
		return desc;
	}

}
