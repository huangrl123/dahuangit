package com.dahuangit.iots.perception.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;

@Component
public class PerceptionRuntimeLogDao extends BaseDao<PerceptionRuntimeLog, Integer> {

	public void addOrUpdatePerceptionRuntimeLog(PerceptionRuntimeLog perceptionRuntimeLog) {
		this.addOrUpdate(perceptionRuntimeLog);
	}

	/**
	 * 获取最近的当前状态参数的运行日志
	 * 
	 * @param perceptionId
	 * @param paramId
	 * @return
	 */
	public PerceptionRuntimeLog getLastPerceptionRuntimeLogByParam(Integer perceptionId, Integer paramId) {
		String hql = "from PerceptionRuntimeLog log where log.perceptionId=? and log.perceptionParamId=? order by log.createDateTime desc";

		List<PerceptionRuntimeLog> list = this.find(hql, perceptionId, paramId);

		if (null == list || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}
	
}
