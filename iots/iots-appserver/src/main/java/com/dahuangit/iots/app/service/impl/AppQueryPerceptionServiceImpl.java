package com.dahuangit.iots.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.app.dto.request.AppGetPerceptionListRequest;
import com.dahuangit.iots.app.dto.request.AppGetPerceptionRuntimeLogListRequest;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionListResponse;
import com.dahuangit.iots.app.dto.response.AppGetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.dto.response.PerceptionInfo;
import com.dahuangit.iots.app.dto.response.PerceptionRuntimeLogDto;
import com.dahuangit.iots.app.dto.response.PerceptionRuntimeLogInfo;
import com.dahuangit.iots.app.service.AppQueryPerceptionService;
import com.dahuangit.iots.manager.dao.UserDao;
import com.dahuangit.iots.manager.entry.User;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionRuntimeLogDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;
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

			boolean isOnline = ClientConnectorPool.getInstance().containsClientConnector(info.getPerceptionAddr());
            info.setIsOnline(isOnline);
			
			perceptionDtos.add(info);
		}

		response.setPerceptionDtos(perceptionDtos);

		return response;
	}
}
