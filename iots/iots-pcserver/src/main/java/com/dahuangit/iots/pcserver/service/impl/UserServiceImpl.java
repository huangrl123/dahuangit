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

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.pcserver.dao.MgrLogDao;
import com.dahuangit.iots.pcserver.dao.UserDao;
import com.dahuangit.iots.pcserver.dto.request.SaveUserRequest;
import com.dahuangit.iots.pcserver.dto.request.QueryUserByPageRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.pcserver.service.UserService;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.entry.MgrLog;
import com.dahuangit.iots.perception.entry.User;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.util.bean.dto.DtoBuilder;
import com.dahuangit.util.date.DateUtils;

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
	 * 添加用户
	 * 
	 * @param request
	 */
	public void addUser(SaveUserRequest request) {
		User u = new User();
		u.setUserName(request.getUserName());
		u.setUserAbbr(request.getUserAbbr());
		u.setPassword("111111");
		this.userDao.add(u);
	}

	/**
	 * 修改用户
	 * 
	 * @param request
	 */
	public void updateUser(SaveUserRequest request) {
		User u = this.userDao.get(User.class, request.getUserId());
		u.setUserName(request.getUserName());
		u.setUserAbbr(request.getUserAbbr());
		this.userDao.update(u);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(Integer userId) {
		User u = this.userDao.get(User.class, userId);
		this.userDao.delete(u);
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
		List<NoticeInfo> list = perceptionService.getNoticeInfos(userId);
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

	/**
	 * 通过分页查询用户
	 * 
	 * @param req
	 * @return
	 */
	public PageQueryResult<QueryUserByPageResponse> queryUserByPage(QueryUserByPageRequest req) {
		PageQueryResult<QueryUserByPageResponse> pageQueryResult = new PageQueryResult<QueryUserByPageResponse>();
		StringBuffer hql = new StringBuffer("from User u where 1=1 ");
		StringBuffer counthql = new StringBuffer("select count(*) from User u where 1=1 ");
		StringBuffer condition = new StringBuffer();

		List<Object> values = new ArrayList<Object>();
		if (null != req.getIsOnline() && !"".equals(req.getIsOnline())) {
			condition.append(" and u.isOnline=?");
			values.add(req.getIsOnline());
		}

		if (null != req.getUserName() && !"".equals(req.getUserName())) {
			condition.append(" and u.userName like?");
			values.add("%" + req.getUserName() + "%");
		}

		Long count = this.userDao.findRecordsCount(counthql.append(condition).toString(),
				values.toArray(new Object[values.size()]));

		List<User> list = this.userDao.findByPage(hql.append(condition).toString(), req.getStart(), req.getLimit(),
				values.toArray(new Object[values.size()]));

		List<QueryUserByPageResponse> rows = new ArrayList<QueryUserByPageResponse>();
		for (User u : list) {
			QueryUserByPageResponse pageResponse = DtoBuilder.buildDto(QueryUserByPageResponse.class, u);

			if (null != u.getLastLoginTime()) {
				pageResponse.setLastLoginTime(DateUtils.format(u.getLastLoginTime()));
			}

			rows.add(pageResponse);
		}

		pageQueryResult.setTotal(count);
		pageQueryResult.setRows(rows);

		return pageQueryResult;
	}
}
