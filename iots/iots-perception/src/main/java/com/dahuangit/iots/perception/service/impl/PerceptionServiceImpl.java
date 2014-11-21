package com.dahuangit.iots.perception.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.ValueTextModel;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionParamDao;
import com.dahuangit.iots.perception.dao.PerceptionParamValueDao;
import com.dahuangit.iots.perception.dao.PerceptionRuntimeLogDao;
import com.dahuangit.iots.perception.dto.opm.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.opm.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionParamValueInfo;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.service.RemoteCtrlService;
import com.dahuangit.iots.perception.service.RemoteQueryService;
import com.dahuangit.iots.perception.tcpserver.dto.StatusParam;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.util.bean.dto.DtoBuilder;
import com.dahuangit.util.coder.ByteUtils;

@Component
@Transactional
public class PerceptionServiceImpl implements PerceptionService {

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private PerceptionParamDao perceptionParamDao = null;

	@Autowired
	private RemoteCtrlService remoteCtrlService = null;

	@Autowired
	private RemoteQueryService remoteQueryService = null;

	@Autowired
	private PerceptionRuntimeLogDao perceptionRuntimeLogDao = null;

	@Autowired
	private PerceptionParamValueDao perceptionParamValueDao = null;

	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit) {
		PageQueryResult<PerceptionOpResponse> pageQueryResult = new PageQueryResult<PerceptionOpResponse>();

		List<PerceptionOpResponse> rows = new ArrayList<PerceptionOpResponse>();
		List<Perception> list = this.perceptionDao.findPerceptionByPage(start, limit);

		for (Perception p : list) {
			PerceptionOpResponse por = DtoBuilder.buildDto(PerceptionOpResponse.class, p);
			rows.add(por);
		}

		Long totalCount = this.perceptionDao.findPerceptionCount();

		pageQueryResult.setResults(rows);
		pageQueryResult.setTotalCount(totalCount);

		return pageQueryResult;
	}

	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req) {
		PageQueryResult<PerceptionRuntimeLogResponse> pageQueryResult = new PageQueryResult<PerceptionRuntimeLogResponse>();

		List<PerceptionRuntimeLogResponse> rows = new ArrayList<PerceptionRuntimeLogResponse>();
		String listHql = "from PerceptionRuntimeLog p where p.perceptionId=? order by p.createDateTime desc";
		List<PerceptionRuntimeLog> list = this.perceptionRuntimeLogDao.findByPage(listHql, req.getStart(),
				req.getLimit(), req.getPerceptionId());

		for (PerceptionRuntimeLog p : list) {
			PerceptionRuntimeLogResponse por = DtoBuilder.buildDto(PerceptionRuntimeLogResponse.class, p);
			rows.add(por);
		}

		String countHql = "select count(*) from PerceptionRuntimeLog";
		Long totalCount = this.perceptionRuntimeLogDao.findRecordsCount(countHql);

		pageQueryResult.setResults(rows);
		pageQueryResult.setTotalCount(totalCount);

		return pageQueryResult;
	}

	// 进行远程控制，如果成功，则保存数据库
	public void remoteCtrlPerception(RemoteCtrlPerceptionRequest req) throws Exception {
		PerceptionFrame frame = null;
		int opt = req.getOpt().intValue();
		int value = 0;

		Perception p = this.perceptionDao.get(Perception.class, req.getPerceptionId());
		String addr = p.getPerceptionAddr();

		try {

			StatusParam statusParam = new StatusParam();

			switch (opt) {
			case 3:
			case 4:
				value = req.getSwitchStatus().intValue();
				byte switchStatus = ByteUtils.oneIntToByteArray(value);
				statusParam.setSwitchStatus(switchStatus);
				break;

			case 5:
			case 6:
				value = req.getRotateStatus().intValue();
				byte rotateStatus = ByteUtils.oneIntToByteArray(value);
				statusParam.setRotateStatus(rotateStatus);
				break;
			}

			frame = this.remoteCtrlService.doRemoteCtrl(addr, req.getOpt(), statusParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		if (null != frame) {
			addPerceptionRuntimeLog(addr, req.getOpt(), 0, value);
		}

	}

	/**
	 * 添加日志
	 * 
	 * @param addr
	 * @param opt
	 * @param param
	 *            感知端设备参数,单一参数远程控制类传0，例如远程开，远程正转
	 * @param value
	 *            实际值
	 */
	public void addPerceptionRuntimeLog(String addr, int opt, int param, int value) {
		// 更新感知端设备信息
		Perception p = this.perceptionDao.findUniqueBy("perceptionAddr", addr);
		if (null == p) {
			p = new Perception();
			p.setPerceptionTypeId(1);// 2+2 perception
			p.setPerceptionAddr(addr);
			p.setPerceptionName("2+2测试设备");
			p.setInstallSite("测试环境");
			p.setCreateDateTime(new Date());
			p.setLastCommTime(new Date());
			this.perceptionDao.add(p);
		} else {
			p.setLastCommTime(new Date());
			this.perceptionDao.update(p);
		}

		// 保存感知端运行日志
		Integer ppvId = null;
		Integer paramTypeId = null;
		switch (opt) {
		case 1:
		case 2:
			paramTypeId = param;
			break;
		case 3:
		case 4:
			paramTypeId = 1;// 开关状态

			break;
		case 5:
		case 6:
			paramTypeId = 2;// 旋转状态
			break;
		}

		ppvId = this.perceptionParamValueDao.getPerceptionParamValueInfoId(1, paramTypeId, Integer.valueOf(value));
       
		if(null == ppvId) {
			return;
		}
		
		PerceptionRuntimeLog perceptionRuntimeLog = new PerceptionRuntimeLog();
		perceptionRuntimeLog.setPerceptionId(p.getPerceptionId());
		perceptionRuntimeLog.setPerceptionParamId(paramTypeId);
		perceptionRuntimeLog.setPerceptionParamValueInfoId(ppvId);
		perceptionRuntimeLog.setPerceptionTypeId(p.getPerceptionTypeId());
		perceptionRuntimeLog.setCreateDateTime(new Date());

		this.perceptionRuntimeLogDao.add(perceptionRuntimeLog);
	}

	public ComboboxData getPerceptionParamValueListByParam(Integer paramId) {
		String hql = "from PerceptionParamValueInfo p where p.perceptionParamId=?";
		List<PerceptionParamValueInfo> list = this.perceptionParamValueDao.find(hql, paramId);

		ComboboxData comboboxData = new ComboboxData();
		for (PerceptionParamValueInfo p : list) {
			ValueTextModel valueTextModel = new ValueTextModel();
			valueTextModel.setValue(String.valueOf(p.getPerceptionParamValue()));
			valueTextModel.setText(p.getPerceptionParamValueDesc());
			comboboxData.getRoot().add(valueTextModel);
		}

		return comboboxData;
	}
}
