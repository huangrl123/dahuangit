package com.dahuangit.iots.perception.tcpserver.processor;

import com.dahuangit.iots.perception.tcpserver.dto.response.PerceptionTcpResponse;

/**
 * 远程控制处理
 * 
 * @author 黄仁良
 * 
 */
public interface PerceptionProcessor {
	/**
	 * 远程查询(已停用)
	 * 
	 * @param perceptionId
	 * @return
	 */
	public PerceptionTcpResponse queryRemoteMachine(Integer perceptionId);

	/**
	 * 远程控制(仅适用于2+2设备)
	 * 
	 * @param perceptionId
	 * @param opt
	 * @return
	 */
	public PerceptionTcpResponse remoteOperateMachine(Integer perceptionId, Integer opt);

	/**
	 * 远程控制(通用)
	 * 
	 * @param perceptionId
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public PerceptionTcpResponse remoteOperateMachine(Integer perceptionId, Integer paramId, Integer paramValue);
}
