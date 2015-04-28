package com.dahuangit.iots.pcserver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.pcserver.dao.MgrLogDao;
import com.dahuangit.iots.pcserver.dao.UserDao;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.service.UserService;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;
import com.dahuangit.iots.perception.entry.MgrLog;
import com.dahuangit.iots.perception.entry.User;
import com.dahuangit.iots.perception.service.PerceptionService;

/**
 * 用户服务类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:15:37
 */
@Component
@Transactional
public class UserServiceImpl implements UserService, InitializingBean {

	@Autowired
	private UserDao userDao = null;

	@Autowired
	private MgrLogDao mgrLogDao = null;

	@Autowired
	private SessionFactory sessionFactory = null;

	private ConcurrentMap<Integer, Long> onlineUserMap = new ConcurrentHashMap<Integer, Long>();

	@Autowired
	private PerceptionService perceptionService = null;

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

		user.setLastLoginTime(new Date());
		user.setIsOnline(true);

		// 记录日志
		MgrLog log = new MgrLog();
		log.setCreateDateTime(request.getCreateDateTime());
		log.setLatitude(request.getLatitude());
		log.setLongitude(request.getLongitude());
		log.setMgrLogTypeId(1);
		log.setUserId(user.getUserId());

		mgrLogDao.add(log);

		onlineUserMap.put(user.getUserId(), System.currentTimeMillis());

		return response;
	}

	/**
	 * 退出登录
	 */
	public void logout(Integer userId) {
		User user = this.userDao.get(User.class, userId);
		user.setIsOnline(false);
		onlineUserMap.remove(userId);
	}

	/**
	 * 心跳
	 * 
	 * @param userName
	 */
	public HeartResponse heart(Integer userId) {
		if (onlineUserMap.containsKey(userId)) {
			onlineUserMap.put(userId, System.currentTimeMillis());
		} else {
			User user = this.userDao.get(User.class, userId);
			if (null != user) {
				user.setIsOnline(true);
				onlineUserMap.put(user.getUserId(), System.currentTimeMillis());
			}
		}

		HeartResponse response = new HeartResponse();
		// List<NoticeInfo> list = perceptionService.getNoticeInfos(userId);
		List<NoticeInfo> list = new ArrayList<NoticeInfo>();
		for (int i = 0; i < 10; i++) {
			NoticeInfo info = new NoticeInfo();
			info.setPerceptionId(i);
			info.setPerceptionAddr("13ffdslf" + i + "rrrrr");
			info.setWhen("2015年04月28日11:35:38");
			info.setParamDesc("红外");
			info.setParamValueDesc("离开");
			list.add(info);
		}
		response.setNoticeInfos(list);
		return response;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Session session = sessionFactory.openSession();

		String queryString = "from User u where u.isOnline=true";
		List<User> userList = session.createQuery(queryString).list();

		for (User user : userList) {
			onlineUserMap.put(user.getUserId(), System.currentTimeMillis());
		}

		new StatusMaintenanceJob();
	}

	private class StatusMaintenanceJob implements Runnable {

		public void run() {
			Session session = sessionFactory.openSession();

			while (true) {
				try {
					Thread.sleep(5 * 60 * 1000);

					Transaction tr = session.beginTransaction();

					for (Map.Entry<Integer, Long> entry : onlineUserMap.entrySet()) {
						long now = System.currentTimeMillis();
						long last = entry.getValue();

						long timeout = 30 * 60 * 1000;

						if (now - last > timeout) {
							onlineUserMap.remove(entry.getKey());
							User u = (User) session.get(User.class, entry.getKey());
							u.setIsOnline(false);
							session.update(u);
						}
					}

					tr.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
