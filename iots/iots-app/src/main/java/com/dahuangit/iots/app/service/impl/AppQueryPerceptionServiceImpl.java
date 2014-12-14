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
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionRuntimeLogDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;
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

	/**
	 * app获取感知端列表
	 * 
	 * @return
	 */
	public AppGetPerceptionListResponse appGetPerceptionList(AppGetPerceptionListRequest request) {
		AppGetPerceptionListResponse response = new AppGetPerceptionListResponse();

		String hql = "from Perception p where p.perceptionId>?";
		List<Perception> perceptions = this.perceptionDao.find(hql, request.getLocalMaxPerceptionId());
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

			perceptionDtos.add(info);
		}

		response.setPerceptionDtos(perceptionDtos);

		return response;
	}

	/**
	 * app获取感知端运行日志列表
	 * 
	 * @return
	 */
	public AppGetPerceptionRuntimeLogListResponse appGetPerceptionRuntimeLogList(
			AppGetPerceptionRuntimeLogListRequest request) {
		AppGetPerceptionRuntimeLogListResponse response = new AppGetPerceptionRuntimeLogListResponse();

		String hql = "from PerceptionRuntimeLog p where p.perceptionRuntimeLogId>? order by p.perceptionRuntimeLogId desc";
		List<PerceptionRuntimeLog> perceptionRuntimeLogs = this.perceptionRuntimeLogDao.findByPage(hql, 0, 50,
				request.getLocalMaxPerceptionRuntimeLogId());

		Map<String, List<PerceptionRuntimeLogInfo>> map = new HashMap<String, List<PerceptionRuntimeLogInfo>>();

		for (PerceptionRuntimeLog log : perceptionRuntimeLogs) {
			PerceptionRuntimeLogInfo info = new PerceptionRuntimeLogInfo();

			Integer perceptionRuntimeLogId = log.getPerceptionRuntimeLogId();
			info.setPerceptionRuntimeLogId(perceptionRuntimeLogId);

			info.setPerceptionId(log.getPerceptionId());

			String perceptionName = log.getPerception().getPerceptionName();
			info.setPerceptionName(perceptionName);

			String perceptionAddr = log.getPerception().getPerceptionAddr();
			info.setPerceptionAddr(perceptionAddr);

			Integer perceptionParamId = log.getPerceptionParamId();
			info.setPerceptionParamId(perceptionParamId);

			String perceptionParamName = log.getPerceptionParam().getPerceptionParamName();
			info.setPerceptionParamName(perceptionParamName);

			Integer perceptionTypeId = log.getPerceptionTypeId();
			info.setPerceptionTypeId(perceptionTypeId);

			String perceptionTypeName = log.getPerceptionType().getPerceptionTypeName();
			info.setPerceptionTypeName(perceptionTypeName);

			Integer perceptionParamValueInfoId = log.getPerceptionParamValueInfo().getPerceptionParamValueInfoId();
			info.setPerceptionParamValueInfoId(perceptionParamValueInfoId);

			String perceptionParamValueDesc = log.getPerceptionParamValueInfo().getPerceptionParamValueDesc();
			info.setPerceptionParamValueDesc(perceptionParamValueDesc);

			String createDateTime = DateUtils.format(log.getCreateDateTime());
			info.setCreateDateTime(createDateTime);

			String remark = log.getRemark();
			info.setRemark(remark);

			Date d = DateUtils.parse(info.getCreateDateTime());
			String key = DateUtils.format(d, "yyyy年MM月dd日");
			List<PerceptionRuntimeLogInfo> list = map.get(key);
			if (null != list) {
				list.add(info);
			} else {
				list = new ArrayList<PerceptionRuntimeLogInfo>();
				list.add(info);
				map.put(key, list);
			}
		}

		List<PerceptionRuntimeLogDto> logDtos = new ArrayList<PerceptionRuntimeLogDto>();
		for (Map.Entry<String, List<PerceptionRuntimeLogInfo>> entry : map.entrySet()) {
			PerceptionRuntimeLogDto dto = new PerceptionRuntimeLogDto();
			dto.setGroupTag(entry.getKey());
			dto.setLogInfos(entry.getValue());
			logDtos.add(dto);
		}

		response.setLogDtos(logDtos);
		return response;
	}
}
