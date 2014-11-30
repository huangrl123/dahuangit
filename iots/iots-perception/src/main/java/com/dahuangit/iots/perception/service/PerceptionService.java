package com.dahuangit.iots.perception.service;

import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery6j6MachineResponse;
import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;

public interface PerceptionService {

	PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit);

	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req);

	public ComboboxData getPerceptionParamValueListByParam(Integer paramId);

	public void saveLog(PerceptionTcpDto perceptionTcpDto);

	/**
	 * 远程控制电机
	 * 
	 * @param perceptionId
	 * @param opt
	 */
	public void remoteCtrlMachine(Integer perceptionId, Integer opt);

	/**
	 * 查询2+2电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery2j2MachineResponse remoteQuery2j2Machine(Integer perceptionId);

	/**
	 * 查询6+6电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery6j6MachineResponse remoteQuery6j6Machine(Integer perceptionId);
}
