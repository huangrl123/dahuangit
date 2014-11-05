package com.dahuangit.iots.perception.tcpserver.processor.impl;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.dahuangit.base.exception.GenericException;
import com.dahuangit.iots.perception.constant.KeyConstants;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrameConvertor;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponsePool;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

@Component
public class PerceptionProcessorImpl implements PerceptionProcessor {

	private static final Logger log = Log4jUtils.getLogger(PerceptionProcessorImpl.class);

	/** 客户端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	/** 客户端响应池 */
	private ClientResponsePool clientResponsePool = ClientResponsePool.getInstance();

	private long MAX_TRANSMIT_ROLLING_COUNT = 99999999l;

	private long transmitSeq = 1l;

	@Override
	public PerceptionFrame queryRemoteMachine(String machineAddr) {
		ClientConnector clientConnection = this.clientConnectionPool.getClientConnector(machineAddr);

		if (null == clientConnection) {
			log.debug("当前感知端已失去连接，可能已经离线  machineAddr=" + machineAddr);
			throw new GenericException("当前感知端已失去连接，可能已经离线 machineAddr=" + machineAddr);
		}

		IoSession session = clientConnection.getIoSession();

		PerceptionFrame frame = new PerceptionFrame();
		// 帧序列号
		long seq = this.nextSeq();
		frame.setSeq(seq);

		// 业务类型
		frame.setBusType((byte) 0x01);

		// 电机地址
		frame.setMachineAddr("DSFE432EWR");

		// 操作标识
		frame.setOperateFlag((byte) 0x02);

		byte[] content = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);

		IoBuffer ib = IoBufferUtils.byteToIoBuffer(content);

		session.write(ib);

		long reqTime = System.currentTimeMillis();

		// 等待返回结果
		while (true) {
			// 响应超时
			long nowTime = System.currentTimeMillis();
			long timeout = 15 * 60 * 60;// 默认15分钟
			if ((nowTime - reqTime) > timeout) {
				new GenericException("请求超时");
			}

			Object obj = session.getAttribute(KeyConstants.MINA_SESSION_RESPONSE_KEY);

			if (null != obj) {
				ClientResponse clientResponse = (ClientResponse) obj;
				return clientResponse.getPerceptionFrame();
			}
		}
	}

	@Override
	public PerceptionFrame remoteOperateMachine(String machineAddr, byte opt) {
		ClientConnector clientConnection = this.clientConnectionPool.getClientConnector(machineAddr);

		if (null == clientConnection) {
			log.debug("当前感知端已失去连接，可能已经离线  machineAddr=" + machineAddr);
			throw new GenericException("当前感知端已失去连接，可能已经离线 machineAddr=" + machineAddr);
		}

		IoSession session = clientConnection.getIoSession();

		PerceptionFrame frame = new PerceptionFrame();
		// 帧序列号
		long seq = this.nextSeq();
		frame.setSeq(seq);

		// 业务类型
		frame.setBusType((byte) 0x01);

		// 电机地址
		frame.setMachineAddr("DSFE432EWR");

		// 操作标识
		frame.setOperateFlag(opt);

		byte[] content = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);

		IoBuffer ib = IoBufferUtils.byteToIoBuffer(content);

		session.write(ib);

		long reqTime = System.currentTimeMillis();

		// 等待返回结果
		while (true) {
			// 响应超时
			long nowTime = System.currentTimeMillis();
			long timeout = 15 * 60 * 60;// 默认15分钟
			if ((nowTime - reqTime) > timeout) {
				new GenericException("请求超时");
			}

			Object obj = session.getAttribute(KeyConstants.MINA_SESSION_RESPONSE_KEY);

			if (null != obj) {
				ClientResponse clientResponse = (ClientResponse) obj;
				return clientResponse.getPerceptionFrame();
			}
		}
	}

	/**
	 * 得到下一个发送序号, 到达最大值自动循环
	 * 
	 * @return
	 */
	private synchronized long nextSeq() {
		transmitSeq++;

		if (transmitSeq > MAX_TRANSMIT_ROLLING_COUNT) {
			transmitSeq = 1;
		}

		return transmitSeq;
	}

}
