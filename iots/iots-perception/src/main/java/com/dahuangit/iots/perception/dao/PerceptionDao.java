package com.dahuangit.iots.perception.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.dto.request.FindPerceptionByPageReq;
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

	public List<Perception> findPerceptionByPage(FindPerceptionByPageReq req) {
		StringBuffer hql = new StringBuffer("from Perception p where 1=1 ");
		if (req.getPerceptionAddr() != null && !"".equals(req.getPerceptionAddr())) {
			hql.append(" and p.perceptionAddr like'%");
			hql.append(req.getPerceptionAddr());
			hql.append("%'");
		}

		if (req.getPerceptionTypeId() != null) {
			hql.append(" and p.perceptionTypeId=");
			hql.append(req.getPerceptionTypeId());
		}

		if (req.getOnlineStatus() != null) {
			hql.append(" and p.onlineStatus=");
			hql.append(req.getOnlineStatus());
		}

		hql.append(" order by p.createDateTime desc, p.perceptionAddr asc");

		return this.findByPage(hql.toString(), req.getStart(), req.getLimit());
	}

	public Long findPerceptionCount(FindPerceptionByPageReq req) {
		StringBuffer hql = new StringBuffer("select count(*) from Perception p  where 1=1");
		if (req.getPerceptionAddr() != null && !"".equals(req.getPerceptionAddr())) {
			hql.append(" and p.perceptionAddr like'%");
			hql.append(req.getPerceptionAddr());
			hql.append("%'");
		}

		if (req.getPerceptionTypeId() != null) {
			hql.append(" and p.perceptionTypeId=");
			hql.append(req.getPerceptionTypeId());
		}

		if (req.getOnlineStatus() != null) {
			hql.append(" and p.onlineStatus=");
			hql.append(req.getOnlineStatus());
		}

		return this.findRecordsCount(hql.toString());
	}

	public Perception findPerceptionByAddr(String addr) {
		return this.findUniqueBy("perceptionAddr", addr);
	}

	public void addPerception(Perception perception) {
		this.add(perception);
	}
}
