package com.dahuangit.iots.perception.tcpserver.pool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * 客户端连接
 * 
 * @author 黄仁良
 * 
 */
public class ClientConnector {

	/** 客户端唯一标识 */
	private String clientKey = null;

	/** 最后通信时间 */
	private Date lastCommTime = new Date();

	/** mina iosession */
	private IoSession ioSession = null;

	/** 当前状态 key:状态参数id，value ：状态值 */
	private Map<Integer, Integer> currentStatus = new HashMap<Integer, Integer>();

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	public Date getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(Date lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public Map<Integer, Integer> getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Map<Integer, Integer> currentStatus) {
		this.currentStatus = currentStatus;
	}

}
