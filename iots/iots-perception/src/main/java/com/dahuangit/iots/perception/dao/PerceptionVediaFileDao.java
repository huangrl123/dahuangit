package com.dahuangit.iots.perception.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.perception.entry.PerceptionVediaFile;

/**
 * 感知端视频文件dao类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:41:25
 */
@Component
public class PerceptionVediaFileDao extends BaseDao<PerceptionVediaFile, Integer> {

	public List<PerceptionVediaFile> findPerceptionVediaFileByPage(Integer perceptionId, Integer start, Integer limit) {
		String hql = "from PerceptionVediaFile p where p.perceptionId=? order by p.createDateTime desc";
		return this.findByPage(hql, start, limit, perceptionId);
	}

	public Long findPerceptionVediaFileCount(Integer perceptionId) {
		String hql = "select count(*) from PerceptionVediaFile p where p.perceptionId=?";
		return this.findRecordsCount(hql, perceptionId);
	}
}
