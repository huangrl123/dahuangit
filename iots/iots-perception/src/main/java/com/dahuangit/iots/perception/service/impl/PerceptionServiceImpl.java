package com.dahuangit.iots.perception.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;
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
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery6j6MachineResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionParamValueInfo;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine2j2SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine6j6SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.response.PerceptionTcpResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerCtrlMachineResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine2j2StatusResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine6j6StatusResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
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
	private PerceptionRuntimeLogDao perceptionRuntimeLogDao = null;

	@Autowired
	private PerceptionParamValueDao perceptionParamValueDao = null;

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	/** 感知端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit) {
		PageQueryResult<PerceptionOpResponse> pageQueryResult = new PageQueryResult<PerceptionOpResponse>();

		List<PerceptionOpResponse> rows = new ArrayList<PerceptionOpResponse>();
		List<Perception> list = this.perceptionDao.findPerceptionByPage(start, limit);

		for (Perception p : list) {
			PerceptionOpResponse por = DtoBuilder.buildDto(PerceptionOpResponse.class, p);

			if (this.clientConnectionPool.containsClientConnector(p.getPerceptionAddr())) {
				por.setOnlineStatus("在线");
			} else {
				por.setOnlineStatus("离线");
			}

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

		String countHql = "select count(*) from PerceptionRuntimeLog p where p.perceptionId=?";
		Long totalCount = this.perceptionRuntimeLogDao.findRecordsCount(countHql, req.getPerceptionId());

		pageQueryResult.setResults(rows);
		pageQueryResult.setTotalCount(totalCount);

		return pageQueryResult;
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

	public void saveLog(PerceptionTcpDto perceptionTcpDto) {
		String addr = perceptionTcpDto.getMachineAddr();

		// 判断该感知端是否已经在系统中有记录
		Perception p = perceptionDao.findUniqueBy("perceptionAddr", addr);
		if (null != p) {
			p.setLastCommTime(new Date());
			perceptionDao.update(p);
		} else {
			p = new Perception();
			p.setCreateDateTime(new Date());
			p.setInstallSite("测试环境");
			p.setLastCommTime(new Date());
			p.setPerceptionAddr(addr);
			p.setPerceptionName("测试设备");
			p.setPerceptionTypeId(1);// 2+2
			perceptionDao.add(p);
		}

		// 记录该感知端的参数日志
		if (perceptionTcpDto instanceof ServerCtrlMachineResponse) {// 简单的响应，比如远程控制类
			PerceptionTcpResponse perceptionTcpResponse = (PerceptionTcpResponse) perceptionTcpDto;
			byte opt = perceptionTcpResponse.getOperateFlag();
			PerceptionRuntimeLog perceptionRuntimeLog = null;
			switch (opt) {
			case 0x03:// 电机1远程正转控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 179, 1, perceptionTcpDto.getHex());
				break;
			case 0x04:// 电机1远程反转控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 179, 2, perceptionTcpDto.getHex());
				break;
			case 0x05:// 电机1远程通电控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 180, 1, perceptionTcpDto.getHex());
				break;
			case 0x06:// 电机1远程断电控制的响应,当电机旋转状态参数方式来处理
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 179, 3, perceptionTcpDto.getHex());
				break;
			case 0x07:// 远程I2C开的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 182, 1, perceptionTcpDto.getHex());
				break;
			case 0x08:// 远程I2C关的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 182, 2, perceptionTcpDto.getHex());
				break;
			case 0x09:// 电机2远程控制旋转状态 正转
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 187, 1, perceptionTcpDto.getHex());
				break;
			case 0x0A:// 电机2控制旋转状态2 反转
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 187, 2, perceptionTcpDto.getHex());
				break;
			case 0x0B:// 电机2通电控制
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 188, 1, perceptionTcpDto.getHex());
				break;
			case 0x0C:// 电机2断电控制，以电机2的旋转状态的方式来处理
				perceptionRuntimeLog = createPerceptionRuntimeLog(p, 187, 3, perceptionTcpDto.getHex());
				break;
			}

			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog);
		}

		else if (perceptionTcpDto instanceof Machine2j2SendStatusRequest) {// 2+2电机上传状态的请求
			Machine2j2SendStatusRequest request = (Machine2j2SendStatusRequest) perceptionTcpDto;

			// 电机1旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_machine1RotateStatus = this.createPerceptionRuntimeLog(p, 179,
					(int) request.getMachine1RotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_machine1RotateStatus);

			// i2c状态
			PerceptionRuntimeLog perceptionRuntimeLog_i2cStatus = this.createPerceptionRuntimeLog(p, 182,
					ByteUtils.byteArrToInt(request.getI2cStatus()), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_i2cStatus);

			// 红外状态
			PerceptionRuntimeLog perceptionRuntimeLog_infraredStatus = this.createPerceptionRuntimeLog(p, 183,
					(int) request.getInfraredStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_infraredStatus);

			// 电机2旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_machine2RotateStatus = this.createPerceptionRuntimeLog(p, 187,
					(int) request.getMachine2RotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_machine2RotateStatus);

			// 按键状态
			PerceptionRuntimeLog perceptionRuntimeLog_pressKey = this.createPerceptionRuntimeLog(p, 189,
					(int) request.getPressKeyStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_pressKey);
		}

		else if (perceptionTcpDto instanceof ServerQueryMachine6j6StatusResponse) {// 6+6远程查询的响应
			ServerQueryMachine6j6StatusResponse response = (ServerQueryMachine6j6StatusResponse) perceptionTcpDto;
			// 开关状态
			PerceptionRuntimeLog perceptionRuntimeLog_switch = this.createPerceptionRuntimeLog(p, 180,
					(int) response.getSwitchStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_switch);

			// 旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_rotateStatus = this.createPerceptionRuntimeLog(p, 179,
					(int) response.getRotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_rotateStatus);

			// 红外状态
			PerceptionRuntimeLog perceptionRuntimeLog_infraredStatus = this.createPerceptionRuntimeLog(p, 183,
					(int) response.getInfraredStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_infraredStatus);

			// 压力状态
			PerceptionRuntimeLog perceptionRuntimeLog_pressureStatus = this.createPerceptionRuntimeLog(p, 185,
					(int) response.getPressureStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_pressureStatus);

			// 振动状态
			PerceptionRuntimeLog perceptionRuntimeLog_vibrateStatus = this.createPerceptionRuntimeLog(p, 184,
					(int) response.getVibrateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_vibrateStatus);

			// 接近状态
			PerceptionRuntimeLog perceptionRuntimeLog_approachStatus = this.createPerceptionRuntimeLog(p, 186,
					(int) response.getApproachStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_approachStatus);

		}

		else if (perceptionTcpDto instanceof Machine6j6SendStatusRequest) {// 6+6电机上传状态的请求
			Machine6j6SendStatusRequest request = (Machine6j6SendStatusRequest) perceptionTcpDto;
			// 开关状态
			PerceptionRuntimeLog perceptionRuntimeLog_switch = this.createPerceptionRuntimeLog(p, 180,
					(int) request.getSwitchStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_switch);

			// 旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_rotateStatus = this.createPerceptionRuntimeLog(p, 179,
					(int) request.getRotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_rotateStatus);

			// 红外状态
			PerceptionRuntimeLog perceptionRuntimeLog_infraredStatus = this.createPerceptionRuntimeLog(p, 183,
					(int) request.getInfraredStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_infraredStatus);

			// 压力状态
			PerceptionRuntimeLog perceptionRuntimeLog_pressureStatus = this.createPerceptionRuntimeLog(p, 185,
					(int) request.getPressureStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_pressureStatus);

			// 振动状态
			PerceptionRuntimeLog perceptionRuntimeLog_vibrateStatus = this.createPerceptionRuntimeLog(p, 184,
					(int) request.getVibrateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_vibrateStatus);

			// 接近状态
			PerceptionRuntimeLog perceptionRuntimeLog_approachStatus = this.createPerceptionRuntimeLog(p, 186,
					(int) request.getApproachStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_approachStatus);
		}
	}

	/**
	 * 生成PerceptionRuntimeLog对象
	 * 
	 * @param perception
	 * @param perceptionParamId
	 * @param perceptionParamValue
	 * @return
	 */
	private PerceptionRuntimeLog createPerceptionRuntimeLog(Perception perception, Integer perceptionParamId,
			Integer perceptionParamValue, String hex) {
		PerceptionRuntimeLog perceptionRuntimeLog = new PerceptionRuntimeLog();
		perceptionRuntimeLog.setCreateDateTime(new Date());
		perceptionRuntimeLog.setPerceptionId(perception.getPerceptionId());
		perceptionRuntimeLog.setPerceptionParamId(perceptionParamId);
		perceptionRuntimeLog.setHex(hex);

		Integer perceptionParamValueInfoId = this.perceptionParamValueDao.getPerceptionParamValueInfoId(
				perceptionParamId, perceptionParamValue);

		perceptionRuntimeLog.setPerceptionParamValueInfoId(perceptionParamValueInfoId);
		perceptionRuntimeLog.setPerceptionTypeId(perception.getPerceptionTypeId());
		return perceptionRuntimeLog;
	}

	/**
	 * 远程控制电机
	 * 
	 * @param perceptionId
	 * @param opt
	 */
	public void remoteCtrlMachine(Integer perceptionId, Integer opt) {
		PerceptionTcpResponse response = perceptionProcessor.remoteOperateMachine(perceptionId, opt);

		if (null == response || response.getResult() != 1) {
			throw new RuntimeException("电机响应超时");
		}
	}

	/**
	 * 查询2+2电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery2j2MachineResponse remoteQuery2j2Machine(Integer perceptionId) {
		ServerQueryMachine2j2StatusResponse response = null;
		String msg = null;
		try {
			response = (ServerQueryMachine2j2StatusResponse) this.perceptionProcessor.queryRemoteMachine(perceptionId);
		} catch (Exception e) {
			msg = "感知端查询响应的帧序号与其他请求的响应错乱";
			e.printStackTrace();
		}

		RemoteQuery2j2MachineResponse r = new RemoteQuery2j2MachineResponse();

		if (response == null || response.getResult() != (byte) 0x01) {
			r.setSuccess(false);
			if (msg == null) {
				r.setMsg(msg);
			} else {
				r.setMsg("响应超时");
			}
		} else {
			r.setSuccess(true);
			r.setHex(response.getHex());

			r.setPerceptionId(perceptionId);

			if (!this.perceptionParamValueDao.existsPerceptionParamValue(179, (int) response.getMachine1RotateStatus())) {
				Log.error("感知端响应非法电机1旋转状态值:" + ByteUtils.byteToHexString(response.getMachine1RotateStatus()));
				throw new RuntimeException("感知端响应非法电机1旋转状态值:"
						+ ByteUtils.byteToHexString(response.getMachine1RotateStatus()));
			}
			r.setMachine1RotateStatus(String.valueOf(response.getMachine1RotateStatus()));

			int i2c = ByteUtils.byteArrToInt(response.getI2cStatus());
			if (!this.perceptionParamValueDao.existsPerceptionParamValue(182, i2c)) {
				Log.error("感知端响应非法i2c状态值:" + ByteUtils.byteArrToHexString(response.getI2cStatus()));
				throw new RuntimeException("感知端响应非法i2c状态值:" + ByteUtils.byteArrToHexString(response.getI2cStatus()));
			}
			r.setI2cStatus(String.valueOf((i2c)));

			// 红外（不可以远程控制）
			String infraredStatus = this.perceptionParamValueDao.getPerceptionParamValueDesc(1, 183,
					(int) response.getInfraredStatus());
			if (null == infraredStatus) {
				Log.error("感知端响应非法红外状态值:" + ByteUtils.byteToHexString(response.getInfraredStatus()));
				throw new RuntimeException("感知端响应非法红外状态值:" + ByteUtils.byteToHexString(response.getInfraredStatus()));
			}
			r.setInfraredStatus(infraredStatus);

			if (!this.perceptionParamValueDao.existsPerceptionParamValue(187, (int) response.getMachine2RotateStatus())) {
				Log.error("感知端响应非法电机2旋转状态值:" + ByteUtils.byteToHexString(response.getMachine2RotateStatus()));
				throw new RuntimeException("感知端响应非法电机2旋转状态值:"
						+ ByteUtils.byteToHexString(response.getMachine2RotateStatus()));
			}
			r.setMachine2RotateStatus(String.valueOf(response.getMachine2RotateStatus()));

			// 按键(不可以远程控制)
			String pressKeyStatus = this.perceptionParamValueDao.getPerceptionParamValueDesc(1, 189,
					(int) response.getPressKeyStatus());
			if (null == pressKeyStatus) {
				Log.error("感知端响应非法按键状态值:" + ByteUtils.byteToHexString(response.getInfraredStatus()));
				throw new RuntimeException("感知端响应非法按键状态值:" + ByteUtils.byteToHexString(response.getInfraredStatus()));
			}
			r.setPressKeyStatus(pressKeyStatus);
		}
		return r;
	}

	/**
	 * 查询6+6电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery6j6MachineResponse remoteQuery6j6Machine(Integer perceptionId) {
		ServerQueryMachine6j6StatusResponse response = (ServerQueryMachine6j6StatusResponse) this.perceptionProcessor
				.queryRemoteMachine(perceptionId);
		RemoteQuery6j6MachineResponse r = new RemoteQuery6j6MachineResponse();

		if (null == response || response.getResult() != (byte) 0x01) {
			r.setSuccess(false);
			r.setMsg("电机响应超时");
		} else {
			r.setRotateStatus(String.valueOf(response.getRotateStatus()));
			r.setSwitchStatus(String.valueOf(response.getSwitchStatus()));
			r.setPerceptionId(perceptionId);
			r.setApproachStatus(String.valueOf(response.getApproachStatus()));
			r.setInfraredStatus(String.valueOf(response.getInfraredStatus()));
			r.setPressureStatus(String.valueOf(response.getPerceptionType()));
			r.setVibrateStatus(String.valueOf(response.getVibrateStatus()));
		}

		return r;
	}

}
