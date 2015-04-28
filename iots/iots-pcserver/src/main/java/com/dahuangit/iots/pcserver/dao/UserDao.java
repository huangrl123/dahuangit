package com.dahuangit.iots.pcserver.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.iots.pcserver.dto.request.QueryUserByPageRequest;
import com.dahuangit.iots.perception.entry.User;

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

	public User getUserByUserName(String userName) {
		String hql = "from User u where u.userName=?";
		User u = this.findUnique(hql, userName);
		return u;
	}

	public List<User> getOnlineUserList() {
		String hql = "from User u where u.isOnline=?";
		List<User> userList = this.find(hql, true);
		return userList;
	}
}
