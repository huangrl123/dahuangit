package com.dahuangit.iots.perception.tcpserver.processor;

import com.dahuangit.iots.perception.tcpserver.dto.StatusParam;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;

/**
 * 远程控制处理
 * 
 * @author 黄仁良
 * 
 */
public interface PerceptionProcessor {
	/**
	 * 查询远程机器工作状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	PerceptionFrame queryRemoteMachine(Integer perceptionId);

	/**
	 * 远程控制
	 * 
	 * @param session
	 * @param opt
	 * @return
	 */
	PerceptionFrame remoteOperateMachine(String machineAddr, byte opt, StatusParam statusParam);
}
