package com.dahuangit.iots.pcserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.pcserver.dao.MgrLogDao;
import com.dahuangit.iots.pcserver.dao.UserDao;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLogoutRequest;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.entry.MgrLog;
import com.dahuangit.iots.pcserver.entry.User;
import com.dahuangit.iots.pcserver.service.UserService;

/**
 * 用户服务类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:15:37
 */
@Component
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao = null;

	@Autowired
	private MgrLogDao mgrLogDao = null;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	public UserLoginResponse login(UserLoginRequest request) {
		UserLoginResponse response = new UserLoginResponse();

		User user = this.userDao.getUserByUserNamePwd(request.getUserName(), request.getPassword());

		if (null == user) {
			throw new RuntimeException("用户名或者密码不正确");
		}

		response.setPassword(user.getPassword());
		response.setRemark(user.getRemark());
		response.setUserAbbr(user.getUserAbbr());
		response.setUserId(user.getUserId());
		response.setUserName(user.getUserName());

		// 记录日志
		MgrLog log = new MgrLog();
		log.setCreateDateTime(request.getCreateDateTime());
		log.setLatitude(request.getLatitude());
		log.setLongitude(request.getLongitude());
		log.setMgrLogTypeId(1);
		log.setUserId(user.getUserId());

		mgrLogDao.add(log);

		return response;
	}

	/**
	 * 退出登录
	 */
	public void logout(UserLogoutRequest request) {
		// 记录日志
		MgrLog log = new MgrLog();
		log.setCreateDateTime(request.getCreateDateTime());
		log.setLatitude(request.getLatitude());
		log.setLongitude(request.getLongitude());
		log.setMgrLogTypeId(1);
		log.setUserId(request.getUserId());

		mgrLogDao.add(log);
	}
}
