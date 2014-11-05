package com.dahuangit.iots.perception.tcpserver.pool;

import org.apache.mina.core.session.IoSession;

import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;

/**
 * 客户端响应
 * 
 * @author 黄仁良
 * 
 */
public class ClientResponse {
	/** 帧序列号(由服务器端产生) */
	private Long seq = null;

	/** 响应达到时间 */
	private long upTime;

	/** 响应之后的实体 */
	private PerceptionFrame perceptionFrame = null;

	/** mina iosession */
	private IoSession ioSession = null;

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public PerceptionFrame getPerceptionFrame() {
		return perceptionFrame;
	}

	public void setPerceptionFrame(PerceptionFrame perceptionFrame) {
		this.perceptionFrame = perceptionFrame;
	}

}
