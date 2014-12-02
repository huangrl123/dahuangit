package com.dahuangit.iots.perception.tcpserver.processor;

import com.dahuangit.iots.perception.tcpserver.dto.response.PerceptionTcpResponse;

/**
 * 远程控制处理
 * 
 * @author 黄仁良
 * 
 */
public interface PerceptionProcessor {
	public PerceptionTcpResponse queryRemoteMachine(Integer perceptionId);

	public PerceptionTcpResponse remoteOperateMachine(Integer perceptionId, Integer opt);
}
