package com.dahuangit.seobi.receiver.dao;

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
	
}
