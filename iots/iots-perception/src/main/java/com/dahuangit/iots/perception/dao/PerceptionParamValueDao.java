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

	public PerceptionParamValueInfo getPerceptionParamValueInfo(Integer perceptionParamId, Integer perceptionParamValue) {
		String hql = "select * from PerceptionParamValueInfo p where p.perceptionParamId=? and p.perceptionParamValue=?";
		PerceptionParamValueInfo perceptionParamValueInfo = this.findUnique(hql, perceptionParamId,
				perceptionParamValue);
		return perceptionParamValueInfo;
	}

	public boolean existsPerceptionParamValue(Integer perceptionParamId, Integer perceptionParamValue) {
		String hql = "select p.perceptionParamValueInfoId from PerceptionParamValueInfo p where p.perceptionParamId=? and p.perceptionParamValue=?";
		Integer pk = this.findUnique(hql, perceptionParamId, perceptionParamValue);

		if (null == pk) {
			return false;
		}

		return true;
	}

	public String getPerceptionParamValueDesc(Integer perceptionParamId, Integer perceptionParamValue) {
		String hql = "select p.perceptionParamValueDesc from PerceptionParamValueInfo p where p.perceptionParamId=? and p.perceptionParamValue=?";
		String desc = this.findUnique(hql, perceptionParamId, perceptionParamValue);
		return desc;
	}

}
