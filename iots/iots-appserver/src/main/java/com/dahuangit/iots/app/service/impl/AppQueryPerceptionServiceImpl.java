package com.dahuangit.iots.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;
import com.dahuangit.iots.app.dto.response.PerceptionInfo;
import com.dahuangit.iots.app.service.AppQueryPerceptionService;
import com.dahuangit.iots.pcserver.dao.UserDao;
import com.dahuangit.iots.pcserver.entry.User;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionRuntimeLogDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.util.date.DateUtils;

/**
 * app查询感知端信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月11日 下午10:18:07
 */
@Component
@Transactional
public class AppQueryPerceptionServiceImpl implements AppQueryPerceptionService {

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private PerceptionRuntimeLogDao perceptionRuntimeLogDao = null;

	@Autowired
	private UserDao userDao = null;

	/**
	 * app获取感知端列表
	 * 
	 * @return
	 */
	public AppGetPerceptionListResponse appGetPerceptionList(AppGetPerceptionListRequest request) {
		AppGetPerceptionListResponse response = new AppGetPerceptionListResponse();

		List<Perception> perceptions = userDao.get(User.class, request.getUserId()).getPerceptions();
		List<PerceptionInfo> perceptionDtos = new ArrayList<PerceptionInfo>();
		for (Perception p : perceptions) {
			PerceptionInfo info = new PerceptionInfo();
			info.setPerceptionId(p.getPerceptionId());
			info.setPerceptionName(p.getPerceptionName());
			info.setPerceptionTypeId(p.getPerceptionType().getPerceptionTypeId());
			info.setPerceptionTypeName(p.getPerceptionType().getPerceptionTypeName());
			info.setPerceptionAddr(p.getPerceptionAddr());
			info.setInstallSite(p.getInstallSite());
			info.setLastCommTime(DateUtils.format(p.getLastCommTime()));

			boolean isOnline = false;
			if (0 == p.getOnlineStatus()) {
				isOnline = false;
			} else if (1 == p.getOnlineStatus()) {
				isOnline = true;
			}

			info.setIsOnline(isOnline);

			perceptionDtos.add(info);
		}

		response.setPerceptionDtos(perceptionDtos);

		return response;
	}
}
