package com.dahuangit.iots.perception.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.ValueTextModel;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionParamDao;
import com.dahuangit.iots.perception.dao.PerceptionParamValueDao;
import com.dahuangit.iots.perception.dao.PerceptionRuntimeLogDao;
import com.dahuangit.iots.perception.dao.PerceptionTypeDao;
import com.dahuangit.iots.perception.dao.UserDao;
import com.dahuangit.iots.perception.dto.request.DelPerceptionVideoRequest;
import com.dahuangit.iots.perception.dto.request.FindPerceptionByPageReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.request.ParamInfo;
import com.dahuangit.iots.perception.dto.request.ParamInfoList;
import com.dahuangit.iots.perception.dto.request.PerceptionParamStatusRequest;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.request.SavePerceptionReq;
import com.dahuangit.iots.perception.dto.request.UploadCurStatusParamRequest;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionParamStatusQueryResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionParam;
import com.dahuangit.iots.perception.entry.PerceptionParamValueInfo;
import com.dahuangit.iots.perception.entry.PerceptionRuntimeLog;
import com.dahuangit.iots.perception.entry.PerceptionType;
import com.dahuangit.iots.perception.entry.User;
import com.dahuangit.iots.perception.enums.ParamType;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine2j2SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.response.PerceptionTcpResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerCtrlMachineResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.bean.dto.DtoBuilder;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.date.DateUtils;
import com.dahuangit.util.net.ftp.FTPHelper;
import com.dahuangit.util.xml.XmlUtils;

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
	private PerceptionTypeDao perceptionTypeDao = null;

	@Autowired
	private UserDao userDao = null;

	@Autowired
	private PerceptionProcessor perceptionProcessor = null;

	/** 感知端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	@Autowired
	protected CastorMarshaller xmlMarshaller = null;

	private Map<Integer, List<NoticeInfo>> noticeMap = new HashMap<Integer, List<NoticeInfo>>();

	private boolean threadStarted = false;

	@Value("${perception.ftpHost}")
	private String ftpHost = null;
	@Value("${perception.ftpUserName}")
	private String ftpUserName = null;
	@Value("${perception.ftpPassword}")
	private String ftpPassword = null;
	@Value("${perception.ftpPort}")
	private Integer ftpPort = null;
	@Value("${perception.ftpDir}")
	private String perceptionFtpDir = null;

	/**
	 * 添加设备
	 * 
	 * @param req
	 * @throws IOException
	 */
	public void addPerception(SavePerceptionReq req) throws IOException {
		Perception p = new Perception();
		p.setPerceptionAddr(req.getPerceptionAddr());
		p.setPerceptionName(req.getPerceptionName());
		p.setPerceptionTypeId(req.getPerceptionTypeId());
		p.setCreateDateTime(new Date());

		User u = this.userDao.get(User.class, req.getUserId());
		List<User> list = new ArrayList<User>();
		list.add(u);
		p.setManagers(list);

		this.perceptionDao.addPerception(p);

		File f = new File(this.perceptionFtpDir + "\\" + p.getPerceptionAddr());
		f.mkdir();
	}

	/**
	 * 修改设备
	 * 
	 * @param req
	 */
	public void updatePerception(SavePerceptionReq req) {
		Perception p = this.perceptionDao.get(Perception.class, req.getPerceptionId());
		p.setPerceptionAddr(req.getPerceptionAddr());
		p.setPerceptionName(req.getPerceptionName());
		p.setCreateDateTime(new Date());
		this.perceptionDao.update(p);
	}

	/**
	 * 修改设备管理员
	 * 
	 * @param req
	 */
	public void updatePerceptionManagers(Integer perceptionId, List<Integer> userIdList) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		if (null == userIdList || userIdList.isEmpty()) {
			p.setManagers(null);
		} else {
			List<User> list = this.userDao.getUserListByIds(userIdList);
			p.setManagers(list);
		}
		this.perceptionDao.update(p);
	}

	/**
	 * 为设备分配管理人员
	 * 
	 * @param perceptionId
	 * @param userIds
	 */
	public void allocationManagersForPerception(Integer perceptionId, List<Integer> userIds) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		// TODO
	}

	/**
	 * 删除设备
	 * 
	 * @param perceptionId
	 */
	public void deletePerception(Integer perceptionId) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		this.perceptionDao.delete(p);

		File f = new File(this.perceptionFtpDir + "\\" + p.getPerceptionAddr());
		f.delete();
	}

	/**
	 * 通过地址查询设备
	 * 
	 * @param addr
	 * @return
	 */
	public Perception findPerceptionByAddr(String addr) {
		return this.perceptionDao.findPerceptionByAddr(addr);
	}

	/**
	 * 获取单个感知端
	 * 
	 * @param perceptionId
	 * @return
	 */
	public PerceptionOpResponse getPerception(Integer perceptionId) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		PerceptionOpResponse por = DtoBuilder.buildDto(PerceptionOpResponse.class, p);
		return por;
	}

	public List<QueryUserByPageResponse> getRelateUser(Integer perceptionId) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);

		List<User> userList = p.getManagers();
		List<QueryUserByPageResponse> list = new ArrayList<QueryUserByPageResponse>();

		for (User u : userList) {
			QueryUserByPageResponse response = DtoBuilder.buildDto(QueryUserByPageResponse.class, u);
			list.add(response);
		}

		return list;
	}

	public PageQueryResult<PerceptionOpResponse> findPerceptionByPage(FindPerceptionByPageReq req) {
		PageQueryResult<PerceptionOpResponse> pageQueryResult = new PageQueryResult<PerceptionOpResponse>();

		List<PerceptionOpResponse> rows = new ArrayList<PerceptionOpResponse>();
		List<Perception> list = this.perceptionDao.findPerceptionByPage(req);

		for (Perception p : list) {
			PerceptionOpResponse por = DtoBuilder.buildDto(PerceptionOpResponse.class, p);
			ClientConnector clientConnector = this.clientConnectionPool.getClientConnector(p.getPerceptionAddr());
			if (null != clientConnector) {
				por.setOnlineStatus(1);
				p.setOnlineStatus(1);
			} else {
				por.setOnlineStatus(0);
				p.setOnlineStatus(0);
			}
			rows.add(por);
		}

		Long totalCount = this.perceptionDao.findPerceptionCount(req);

		pageQueryResult.setRows(rows);
		pageQueryResult.setTotal(totalCount);

		return pageQueryResult;
	}

	/**
	 * 获取到所有的设备类型
	 * 
	 * @return
	 */
	public List<PerceptionType> getAllPerceptionTypes() {
		return this.perceptionTypeDao.getAll();
	}

	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req) {
		PageQueryResult<PerceptionRuntimeLogResponse> pageQueryResult = new PageQueryResult<PerceptionRuntimeLogResponse>();

		List<PerceptionRuntimeLogResponse> rows = new ArrayList<PerceptionRuntimeLogResponse>();

		StringBuffer listHql = new StringBuffer("from PerceptionRuntimeLog p where 1=1");
		StringBuffer countHql = new StringBuffer("select count(*) from PerceptionRuntimeLog p where 1=1");

		StringBuffer conditionStr = new StringBuffer();
		List<Object> values = new ArrayList<Object>();

		if (null != req.getPerceptionId()) {
			conditionStr.append(" and p.perceptionId=?");
			values.add(req.getPerceptionId());
		}

		if (null != req.getParamId()) {
			conditionStr.append(" and p.perceptionParamId=? ");
			values.add(req.getParamId());
		}

		if (null != req.getPerceptionParamValue()) {
			conditionStr.append(" and p.perceptionParamValueInfo.perceptionParamValue=? ");
			values.add(req.getPerceptionParamValue());
		}

		String startTimeStr = req.getSartTime();
		String endTimeStr = req.getEndTime();
		if (null != startTimeStr && !"".equals(startTimeStr) && null != endTimeStr && !"".equals(endTimeStr)) {
			startTimeStr = startTimeStr.trim() + " 00:00:00";
			Date startTime = DateUtils.parse(startTimeStr);
			conditionStr.append(" and p.createDateTime>=? ");
			values.add(startTime);

			endTimeStr = endTimeStr.trim() + " 23:59:59";
			Date endTime = DateUtils.parse(endTimeStr);
			conditionStr.append(" and p.createDateTime<=? ");
			values.add(endTime);
		}

		listHql = listHql.append(conditionStr);
		listHql = listHql.append(" order by p.createDateTime desc ");

		List<PerceptionRuntimeLog> list = this.perceptionRuntimeLogDao.findByPage(listHql.toString(), req.getStart(),
				req.getLimit(), values.toArray(new Object[values.size()]));

		for (PerceptionRuntimeLog p : list) {
			PerceptionRuntimeLogResponse por = DtoBuilder.buildDto(PerceptionRuntimeLogResponse.class, p);
			por.setCreateDateTime(DateUtils.format(p.getCreateDateTime()));
			rows.add(por);
		}

		countHql = countHql.append(conditionStr);
		Long totalCount = this.perceptionRuntimeLogDao.findRecordsCount(countHql.toString(),
				values.toArray(new Object[values.size()]));

		pageQueryResult.setRows(rows);
		pageQueryResult.setTotal(totalCount);

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

		if (perceptionTcpDto.getPerceptionType() != 1) {
			return;
		}

		// 判断该感知端是否已经在系统中有记录
		Perception p = perceptionDao.findUniqueBy("perceptionAddr", addr);
		if (null == p) {
			return;
		}

		Integer perceptionId = p.getPerceptionId();

		// 记录该感知端的参数日志
		if (perceptionTcpDto instanceof ServerCtrlMachineResponse) {// 简单的响应，比如远程控制类
			PerceptionTcpResponse perceptionTcpResponse = (PerceptionTcpResponse) perceptionTcpDto;
			int opt = perceptionTcpResponse.getOperateFlag();
			PerceptionRuntimeLog perceptionRuntimeLog = null;
			switch (opt) {
			case 0x03:// 电机1远程正转控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 179, 1, perceptionTcpDto.getHex());
				break;
			case 0x04:// 电机1远程反转控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 179, 2, perceptionTcpDto.getHex());
				break;
			case 0x05:// 电机1远程通电控制的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 180, 1, perceptionTcpDto.getHex());
				break;
			case 0x06:// 电机1远程断电控制的响应,当电机旋转状态参数方式来处理
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 179, 3, perceptionTcpDto.getHex());
				break;
			case 0x07:// 远程I2C开的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 182, 1, perceptionTcpDto.getHex());
				break;
			case 0x08:// 远程I2C关的响应
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 182, 2, perceptionTcpDto.getHex());
				break;
			case 0x09:// 电机2远程控制旋转状态 正转
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 187, 1, perceptionTcpDto.getHex());
				break;
			case 0x0A:// 电机2控制旋转状态2 反转
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 187, 2, perceptionTcpDto.getHex());
				break;
			case 0x0B:// 电机2通电控制
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 188, 1, perceptionTcpDto.getHex());
				break;
			case 0x0C:// 电机2断电控制，以电机2的旋转状态的方式来处理
				perceptionRuntimeLog = createPerceptionRuntimeLog(perceptionId, 187, 3, perceptionTcpDto.getHex());
				break;
			}

			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog);
		}

		else if (perceptionTcpDto instanceof Machine2j2SendStatusRequest) {// 2+2电机上传状态的请求
			Machine2j2SendStatusRequest request = (Machine2j2SendStatusRequest) perceptionTcpDto;

			// 电机1旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_machine1RotateStatus = this.createPerceptionRuntimeLog(
					perceptionId, 179, (int) request.getMachine1RotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_machine1RotateStatus);

			// i2c状态
			PerceptionRuntimeLog perceptionRuntimeLog_i2cStatus = this.createPerceptionRuntimeLog(perceptionId, 182,
					ByteUtils.byteArrToInt(request.getI2cStatus()), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_i2cStatus);

			// 红外状态
			PerceptionRuntimeLog perceptionRuntimeLog_infraredStatus = this.createPerceptionRuntimeLog(perceptionId,
					183, (int) request.getInfraredStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_infraredStatus);

			// 电机2旋转状态
			PerceptionRuntimeLog perceptionRuntimeLog_machine2RotateStatus = this.createPerceptionRuntimeLog(
					perceptionId, 187, (int) request.getMachine2RotateStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_machine2RotateStatus);

			// 按键状态
			PerceptionRuntimeLog perceptionRuntimeLog_pressKey = this.createPerceptionRuntimeLog(perceptionId, 189,
					(int) request.getPressKeyStatus(), perceptionTcpDto.getHex());
			this.perceptionRuntimeLogDao.add(perceptionRuntimeLog_pressKey);
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
	private PerceptionRuntimeLog createPerceptionRuntimeLog(Integer perceptionId, Integer perceptionParamId,
			Integer perceptionParamValue, String hex) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);

		PerceptionRuntimeLog perceptionRuntimeLog = new PerceptionRuntimeLog();
		perceptionRuntimeLog.setCreateDateTime(new Date());
		perceptionRuntimeLog.setPerceptionId(p.getPerceptionId());
		perceptionRuntimeLog.setPerceptionParamId(perceptionParamId);
		perceptionRuntimeLog.setHex(hex);

		Integer perceptionParamValueInfoId = this.perceptionParamValueDao.getPerceptionParamValueInfoId(
				perceptionParamId, perceptionParamValue);

		perceptionRuntimeLog.setPerceptionParamValueInfoId(perceptionParamValueInfoId);
		perceptionRuntimeLog.setPerceptionTypeId(p.getPerceptionTypeId());
		return perceptionRuntimeLog;
	}

	/**
	 * 远程控制电机
	 * 
	 * @param perceptionId
	 * @param opt
	 */
	public void remoteCtrlMachine2j2(RemoteCtrlPerceptionRequest req) {
		Integer opt = null;

		// 电机1旋转状态
		if (req.getParamId() == 179) {
			if (req.getParamValue() == 1) {
				opt = 3;
			} else if (req.getParamValue() == 2) {
				opt = 4;
			} else if (req.getParamValue() == 3) {
				opt = 6;
			}
		}

		// 电机2旋转状态
		if (req.getParamId() == 187) {
			if (req.getParamValue() == 1) {
				opt = 9;// 电机2正转控制
			} else if (req.getParamValue() == 2) {
				opt = 10;// 0x0A: 电机2反转控制
			} else if (req.getParamValue() == 3) {
				opt = 12;// 0x0C：电机2断电控制
			}
		}

		// i2c状态
		if (req.getParamId() == 182) {
			if (req.getParamValue() == 1) {
				opt = 7;// I2C打开控制
			} else if (req.getParamValue() == 2) {
				opt = 8;// I2C关闭控制
			}
		}

		PerceptionTcpResponse response = perceptionProcessor.remoteOperateMachine(req.getPerceptionId(), opt);

		if (null == response || response.getResult() != 1) {
			if (response.getResult() == 2) {
				throw new RuntimeException("感知端响应超时");
			}

			else if (response.getResult() == 3) {
				throw new RuntimeException("您的本次操作无效，请稍后再试...");
			}
		}

		// 电机的正转反转成功之后需要自动执行一次停
		if (req.getParamId() == 179 && opt != 6) {
			PerceptionTcpResponse response1 = perceptionProcessor.remoteOperateMachine(req.getPerceptionId(), opt);
			if (null == response1 || response1.getResult() != 1) {
				if (response1.getResult() == 2) {
					throw new RuntimeException("感知端响应超时");
				}

				else if (response1.getResult() == 3) {
					throw new RuntimeException("您的本次操作无效，请稍后再试...");
				}
			}
		}
	}

	/**
	 * 查询设备状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public PerceptionParamStatusQueryResponse queryPerceptionStatus(PerceptionParamStatusRequest request) {
		PerceptionParamStatusQueryResponse response = new PerceptionParamStatusQueryResponse();

		Perception p = this.perceptionDao.get(Perception.class, request.getPerceptionId());
		response.setPerceptionAddr(p.getPerceptionAddr());
		response.setPerceptionName(p.getPerceptionName());
		response.setPerceptionId(p.getPerceptionId());
		response.setPerceptionTypeId(p.getPerceptionTypeId());
		response.setPerceptionTypeName(p.getPerceptionType().getPerceptionTypeName());

		List<PerceptionParam> list = p.getPerceptionType().getParams();

		ClientConnector clientConnector = this.clientConnectionPool.getClientConnector(p.getPerceptionAddr());
		if (null != clientConnector) {
			response.setOnline(true);
			response.setOnlineStatusDesc("在线");
			p.setOnlineStatus(1);
		} else {
			response.setOnline(false);
			response.setOnlineStatusDesc("离线");
			p.setOnlineStatus(0);
		}

		for (PerceptionParam param : list) {
			Integer paramValue = null;

			// 从内存中取
			if (response.isOnline()) {
				paramValue = clientConnector.getCurrentStatus().get(param.getPerceptionParamId());
			}

			// 从数据库取
			if (null == paramValue) {
				PerceptionRuntimeLog log = this.perceptionRuntimeLogDao.getLastPerceptionRuntimeLogByParam(
						p.getPerceptionId(), param.getPerceptionParamId());

				// 如果数据库中没有日志，则去该参数的默认值
				if (null == log) {
					paramValue = param.getDefaultValue();
				} else {
					paramValue = log.getPerceptionParamValueInfo().getPerceptionParamValue();
				}
			}

			ParamInfo info = new ParamInfo();
			info.setParamDesc(param.getPerceptionParamDesc());
			info.setParamId(param.getPerceptionParamId());
			info.setParamType(param.getParamType().toString());

			// 查询值描述
			if (null != paramValue) {
				info.setParamValue(paramValue);

				if (param.getParamType().equals(ParamType.TEXT)) {
					String valueDesc = this.perceptionParamValueDao.getPerceptionParamValueDesc(
							param.getPerceptionParamId(), paramValue);
					info.setParamValueDesc(valueDesc);
				}
			}

			// 只有初始化状态才会查询下拉选数据
			if (request.isInit()) {
				if (param.getParamType().equals(ParamType.COMBOBOX)) {
					ComboboxData ComboboxData = this.getPerceptionParamValueListByParam(param.getPerceptionParamId());
					info.setComboboxData(ComboboxData);
				}
			}

			if (param.getParamType().equals(ParamType.TEXT)) {
				response.getWarningParamInfos().add(info);
			} else {
				response.getCtrlParamInfos().add(info);
			}
		}

		if (null != p.getLastCommTime()) {
			response.setLastCommTime(DateUtils.format(p.getLastCommTime()));
		}

		return response;
	}

	/**
	 * 查询2+2电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery2j2MachineResponse remoteQuery2j2Machine(Integer perceptionId) {

		PageQueryResult<PerceptionRuntimeLogResponse> pageQueryResult = new PageQueryResult<PerceptionRuntimeLogResponse>();

		List<PerceptionRuntimeLogResponse> rows = new ArrayList<PerceptionRuntimeLogResponse>();
		String listHql = "from PerceptionRuntimeLog p where p.perceptionId=? order by p.createDateTime desc";
		List<PerceptionRuntimeLog> list = this.perceptionRuntimeLogDao.findByPage(listHql, 0, 5, perceptionId);

		RemoteQuery2j2MachineResponse response = new RemoteQuery2j2MachineResponse();

		String perceptionAddr = null;

		for (PerceptionRuntimeLog p : list) {
			// 电机1旋转状态
			if (p.getPerceptionParamId() == 179) {
				Integer machine1RotateStatus = p.getPerceptionParamValueInfo().getPerceptionParamValue();
				response.setMachine1RotateStatus(machine1RotateStatus.toString());
				response.setHex(p.getHex());
				response.setCreateDatetime(DateUtils.format(p.getCreateDateTime()));
				perceptionAddr = p.getPerception().getPerceptionAddr();
				continue;
			}

			// 电机2旋转状态
			if (p.getPerceptionParamId() == 187) {
				Integer machine2RotateStatus = p.getPerceptionParamValueInfo().getPerceptionParamValue();
				response.setMachine2RotateStatus(machine2RotateStatus.toString());
				continue;
			}

			// i2c状态
			if (p.getPerceptionParamId() == 182) {
				Integer i2cStatus = p.getPerceptionParamValueInfo().getPerceptionParamValue();
				response.setI2cStatus(i2cStatus.toString());
				continue;
			}

			// 红外状态
			if (p.getPerceptionParamId() == 183) {
				String infraredStatus = p.getPerceptionParamValueInfo().getPerceptionParamValueDesc();
				response.setInfraredStatus(infraredStatus);

				if (p.getPerceptionParamValueInfo().getPerceptionParamValue() == 1) {
					response.setNeedWarning(true);
				}

				continue;
			}

			// 按键状态
			if (p.getPerceptionParamId() == 189) {
				String pressKeyStatus = p.getPerceptionParamValueInfo().getPerceptionParamValueDesc();
				response.setPressKeyStatus(pressKeyStatus);

				if (p.getPerceptionParamValueInfo().getPerceptionParamValue() == 1) {
					response.setNeedWarning(true);
				}

				continue;
			}

		}

		if (null == response.getMachine1RotateStatus() || null == response.getMachine2RotateStatus()
				|| null == response.getI2cStatus() || null == response.getInfraredStatus()
				|| null == response.getPressKeyStatus()) {
			response.setSuccess(false);
			response.setMsg("感知端上送的状态参数个数不全");
		}

		if (this.clientConnectionPool.containsClientConnector(perceptionAddr)) {
			response.setOnlineStatus("在线");
		} else {
			response.setOnlineStatus("离线");
		}

		return response;
	}

	/**
	 * 设备上传当前状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response uploadCurStatusParam(UploadCurStatusParamRequest request) throws Exception {
		Response response = new Response();

		String addr = request.getPerceptionAddr();
		Perception p = this.perceptionDao.findPerceptionByAddr(addr);

		p.setLastCommTime(new Date());

		ClientConnector clientConnector = this.clientConnectionPool.getClientConnector(addr);
		if (null == clientConnector) {
			p.setOnlineStatus(0);
			throw new RuntimeException("当前设备的tcp心跳未连接或失效");
		} else {
			p.setOnlineStatus(1);
		}

		ParamInfoList pList = XmlUtils
				.xml2obj(xmlMarshaller, request.getPerceptionStatusInfoXml(), ParamInfoList.class);

		Integer perceptionId = p.getPerceptionId();

		Date now = new Date();

		List<NoticeInfo> noticeInfos = new ArrayList<NoticeInfo>();
		for (ParamInfo info : pList.getParamInfos()) {
			NoticeInfo noticeInfo = new NoticeInfo();

			noticeInfo.setWhenL(now.getTime());
			noticeInfo.setWhen(DateUtils.format(now));

			noticeInfo.setPerceptionAddr(addr);
			noticeInfo.setParamId(info.getParamId());
			String paramDesc = perceptionParamDao.getPerceptionParamDesc(info.getParamId());
			noticeInfo.setParamDesc(paramDesc);
			noticeInfo.setParamValue(info.getParamValue());
			String paramValueDesc = perceptionParamValueDao.getPerceptionParamValueDesc(info.getParamId(),
					info.getParamValue());
			noticeInfo.setParamValueDesc(paramValueDesc);

			noticeInfos.add(noticeInfo);
			savePerceptionParamStatus(perceptionId, info.getParamId(), info.getParamValue());
		}

		List<User> managers = p.getManagers();
		for (User u : managers) {
			List<NoticeInfo> list = noticeMap.get(u.getUserId());
			if (null == list) {
				list = new ArrayList<NoticeInfo>();
				list.addAll(noticeInfos);
				noticeMap.put(u.getUserId(), list);
			} else {
				list.addAll(noticeInfos);
			}
		}

		if (!threadStarted) {
			new Thread() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(5 * 60 * 1000);

							long timeout = 10 * 60 * 1000;

							List<NoticeInfo> delList = new ArrayList<NoticeInfo>();

							for (Map.Entry<Integer, List<NoticeInfo>> entry : noticeMap.entrySet()) {

								for (NoticeInfo dto : entry.getValue()) {

									long when = dto.getWhenL();
									long now = new Date().getTime();

									if (now - when > timeout) {
										delList.add(dto);
									}
								}
							}

							noticeMap.values().removeAll(delList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}

		threadStarted = true;

		return response;
	}

	/**
	 * 根据用户id获取通知
	 * 
	 * @param userId
	 * @return
	 */
	public List<NoticeInfo> getNoticeInfos(Integer userId) {
		List<NoticeInfo> list = new ArrayList<NoticeInfo>();
		list = this.noticeMap.get(userId);
		this.noticeMap.remove(userId);
		return list;
	}

	private void savePerceptionParamStatus(Integer perceptionId, Integer paramId, Integer paramValue) {
		// 判断当前状态和数据库中上次当前状态的值是否一致. 如果是一致,则跳过; 如果不一致，则保存数据库
		PerceptionRuntimeLog log = this.perceptionRuntimeLogDao.getLastPerceptionRuntimeLogByParam(perceptionId,
				paramId);

		if (null != log && log.getPerceptionParamValueInfo().getPerceptionParamValue() == paramValue) {
			return;
		} else {
			Perception p = this.perceptionDao.get(Perception.class, perceptionId);
			log = new PerceptionRuntimeLog();
			log.setCreateDateTime(new Date());
			log.setPerceptionId(perceptionId);
			log.setPerceptionParamId(paramId);

			Integer valueId = this.perceptionParamValueDao.getPerceptionParamValueInfoId(paramId, paramValue);
			log.setPerceptionParamValueInfoId(valueId);

			log.setPerceptionTypeId(p.getPerceptionTypeId());
			this.perceptionRuntimeLogDao.add(log);

			ClientConnector clientConnector = this.clientConnectionPool.getClientConnector(p.getPerceptionAddr());
			Map<Integer, Integer> currentStatus = clientConnector.getCurrentStatus();
			// 更新内存中的当状态
			currentStatus.put(paramId, paramValue);
		}
	}

	/**
	 * 远程控制(通用)
	 * 
	 * @param perceptionId
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public Response remoteOperateMachine(Integer perceptionId, Integer paramId, Integer paramValue) {
		Response response = new Response();

		Perception p = this.perceptionDao.get(Perception.class, perceptionId);

		// 如果是2+2
		if (p.getPerceptionTypeId() == 1) {
			RemoteCtrlPerceptionRequest req = new RemoteCtrlPerceptionRequest();
			req.setPerceptionTypeId(1);
			req.setPerceptionId(perceptionId);
			req.setParamId(paramId);
			req.setParamValue(paramValue);

			// 如果不成功，还会发送三次
			for (int i = 0; i < 4; i++) {
				try {
					this.remoteCtrlMachine2j2(req);
					savePerceptionParamStatus(perceptionId, paramId, paramValue);
					return response;
				} catch (Exception e) {
					e.printStackTrace();
					response.setSuccess(false);
					response.setMsg(e.getMessage());
					if (i == 3) {
						return response;
					}
				}

			}

		}

		PerceptionTcpResponse perceptionTcpResponse = this.perceptionProcessor.remoteOperateMachine(perceptionId,
				paramId, paramValue);

		if (perceptionTcpResponse.getResult() != 1) {
			response.setSuccess(false);
			response.setMsg("设备远程控制未成功");
			return response;
		} else {
			savePerceptionParamStatus(perceptionId, paramId, paramValue);
		}

		return response;
	}

	/**
	 * 从ftp服务器上删除设备的视频文件
	 * 
	 * @param req
	 * @throws Exception
	 */
	public void delPerceptionVideo(DelPerceptionVideoRequest req) throws Exception {
		FTPHelper ftpHelper = new FTPHelper(ftpHost, ftpUserName, ftpPassword, ftpPort);
		ftpHelper.deleteFile(req.getPerceptionAddr(), req.getFileName());
	}
}
