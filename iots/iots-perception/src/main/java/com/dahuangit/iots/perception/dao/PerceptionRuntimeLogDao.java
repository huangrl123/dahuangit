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
}
