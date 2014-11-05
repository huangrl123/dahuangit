package com.dahuangit.seobi.receiver.dao;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.seobi.receiver.entry.QQAccount;

@Component("com.dahuangit.seobi.receiver.dao.QQAccountDao")
public class QQAccountDao extends BaseDao<QQAccount, Integer> {

	public void addQQAccount(QQAccount qqAccount) {
		this.add(qqAccount);
	}

	public QQAccount getQQAccount(Integer id) {
		return this.get(QQAccount.class, id);
	}
}
