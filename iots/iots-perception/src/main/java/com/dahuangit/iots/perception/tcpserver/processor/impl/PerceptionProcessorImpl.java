package com.dahuangit.iots.perception.tcpserver.processor.impl;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuangit.base.exception.GenericException;
import com.dahuangit.iots.perception.constant.KeyConstants;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.StatusParam;
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

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private PerceptionService perceptionService = null;

	@Override
	public PerceptionFrame queryRemoteMachine(Integer perceptionId) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		String machineAddr = p.getPerceptionAddr();

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
		frame.setMachineAddr(machineAddr);

		// 操作标识
		int opt = 2;
		frame.setOperateFlag((byte) opt);

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
				session.setAttribute(KeyConstants.MINA_SESSION_RESPONSE_KEY, null);// 不会清掉其他session的值
				PerceptionFrame responseFrame = clientResponse.getPerceptionFrame();
				perceptionService.addPerceptionRuntimeLog(machineAddr, opt, 1, responseFrame.getSwitchStatus());
				perceptionService.addPerceptionRuntimeLog(machineAddr, opt, 2, responseFrame.getRotateStatus());
				return responseFrame;
			}
		}
	}

	@Override
	public PerceptionFrame remoteOperateMachine(String machineAddr, byte opt, StatusParam statusParam) {
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
		frame.setMachineAddr(machineAddr);

		// 操作标识
		frame.setOperateFlag(opt);

		frame.setSwitchStatus(statusParam.getSwitchStatus());
		frame.setRotateStatus(statusParam.getRotateStatus());

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
				PerceptionFrame responseFrame = clientResponse.getPerceptionFrame();
				session.setAttribute(KeyConstants.MINA_SESSION_RESPONSE_KEY, null);
				int value = 0;
				switch (opt) {
				case 0x03:
				case 0x04:
					value = responseFrame.getSwitchStatus();
					break;
				case 0x05:
				case 0x06:
					value = responseFrame.getRotateStatus();
					break;
				}
				perceptionService.addPerceptionRuntimeLog(machineAddr, opt, 0, value);
				return responseFrame;
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
