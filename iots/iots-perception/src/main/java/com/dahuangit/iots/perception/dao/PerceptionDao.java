package com.dahuangit.iots.perception.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.entry.Perception;

/**
 * 感知端dao类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:41:25
 */
@Component
public class PerceptionDao extends BaseDao<Perception, Integer> {

	public void addOrUpdatePerception(Perception perception) {
		this.addOrUpdate(perception);
	}

	public List<Perception> findPerceptionByPage(Integer start, Integer limit) {
		String hql = "from Perception p order by p.perceptionAddr asc";
		return this.findByPage(hql, start, limit);
	}

	public Long findPerceptionCount() {
		String hql = "select count(*) from Perception";
		return this.findRecordsCount(hql);
	}

	public Perception findPerceptionByAddr(String addr) {
		return this.findUniqueBy("perceptionAddr", addr);
	}
}
