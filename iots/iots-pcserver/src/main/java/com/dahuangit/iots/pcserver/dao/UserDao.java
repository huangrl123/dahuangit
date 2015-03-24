package com.dahuangit.iots.pcserver.dao;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.pcserver.entry.User;

/**
 * 用户dao
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:13:33
 */
@Component
public class UserDao extends BaseDao<User, Integer> {

	public User getUserByUserNamePwd(String userName, String password) {
		String hql = "from User u where u.userName=? and u.password=?";
		User u = this.findUnique(hql, userName, password);
		return u;
	}
}
