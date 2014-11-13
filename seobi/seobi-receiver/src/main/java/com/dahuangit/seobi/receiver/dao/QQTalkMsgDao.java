package com.dahuangit.seobi.receiver.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;

@Component("com.dahuangit.seobi.receiver.dao.QQTalkMsgDao")
public class QQTalkMsgDao extends BaseDao<QQTalkMsg, Integer> {

	public void addQQTalkMsg(QQTalkMsg qqTalkMsg) {
		this.add(qqTalkMsg);
	}

	public QQTalkMsg getQQTalkMsg(Integer id) {
		return this.get(QQTalkMsg.class, id);
	}

	public List<QQTalkMsg> getAllNotAnalyzedQQTalkMsg() {
		String hql = "from QQTalkMsg q where q.analyzed=?";

		List<QQTalkMsg> list = this.find(hql, false);

		return list;
	}
	
	
}
