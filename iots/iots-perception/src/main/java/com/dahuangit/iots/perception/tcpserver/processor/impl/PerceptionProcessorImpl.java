package com.dahuangit.iots.perception.tcpserver.processor.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.exception.GenericException;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.response.PerceptionTcpResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine2j2StatusResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine6j6StatusResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.processor.PerceptionProcessor;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.log.Log4jUtils;

@Component
@Transactional
public class PerceptionProcessorImpl implements PerceptionProcessor {

	private static final Logger log = Log4jUtils.getLogger(PerceptionProcessorImpl.class);

	/** 感知端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	/** 感知端当前操作 key:感知端设备地址,value:感知端操作标识 */
	public static Map<String, Integer> perceptionCurOptMap = new HashMap<String, Integer>();

	private long MAX_TRANSMIT_ROLLING_COUNT = 99999999l;

	private long transmitSeq = 1l;

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private PerceptionService perceptionService = null;

	public PerceptionTcpResponse queryRemoteMachine(Integer perceptionId) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		String machineAddr = p.getPerceptionAddr();
		Integer typeId = p.getPerceptionTypeId();
		ClientConnector clientConnection = this.clientConnectionPool.getClientConnector(machineAddr);

		if (null == clientConnection) {
			log.debug("当前感知端已失去连接，可能已经离线  machineAddr=" + machineAddr);
			throw new GenericException("当前设备没有连接到服务器，请联系设备管理员");
		}

		PerceptionTcpResponse response = null;

		if (1 == typeId) {
			response = new ServerQueryMachine2j2StatusResponse();
		}

		else if (2 == typeId) {
			response = new ServerQueryMachine6j6StatusResponse();
		}

		// 判断当前感知端是否有操作，如果有，则返回
		if (perceptionCurOptMap.containsKey(machineAddr)) {
			response.setResult((byte) 0x03);
			return response;
		}

		final IoSession session = clientConnection.getIoSession();

		byte[] content = new byte[69];
		// 帧序列号
		content[0] = (byte) 0xA1;
		content[1] = 0x08;
		long seq = this.nextSeq();
		System.arraycopy(ByteUtils.longToByteArray(seq), 0, content, 2, 8);
		// 帧总长度
		content[10] = (byte) 0xA2;
		content[11] = 0x04;
		System.arraycopy(ByteUtils.intToByteArray(68), 0, content, 12, 4);
		// 业务类型
		content[16] = (byte) 0xA3;
		content[17] = 0x01;
		content[18] = 0x01;
		// CRC32校验和
		content[19] = (byte) 0xA4;
		content[20] = 0x08;
		// 设备类型
		content[29] = (byte) 0xA5;
		content[30] = 0x01;
		byte bytePerceptionTypeId = (byte) p.getPerceptionTypeId().intValue();
		content[31] = bytePerceptionTypeId;
		// 电机地址
		content[32] = (byte) 0xB1;
		content[33] = 0x20;
		System.arraycopy(machineAddr.getBytes(), 0, content, 34, machineAddr.getBytes().length);
		// 操作标识
		content[66] = (byte) 0xB2;
		content[67] = 0x01;
		content[68] = 0x02;
		perceptionCurOptMap.put(machineAddr, 2);

		long crc32l = ByteUtils.byteArrCRC32Value(content);
		System.arraycopy(ByteUtils.longToByteArray(crc32l), 0, content, 21, 8);

		final IoBuffer ib = IoBufferUtils.byteToIoBuffer(content);

		log.debug("服务器远程查询设备请求开始发送,请求帧序号为:[" + seq + "]");

		session.write(ib);

		long reqTime = System.currentTimeMillis();

		// 等待返回结果
		while (true) {

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 响应超时
			long nowTime = System.currentTimeMillis();
			long timeout = 7 * 1000;
			long count = nowTime - reqTime;
			if (count > timeout) {
				log.debug("远程查询电机超时:帧序号seq=" + seq);
				response.setResult((byte) 0x02);
				perceptionCurOptMap.remove(machineAddr);
				return response;
			}

			Object obj = session.getAttribute(seq);

			if (null != obj) {
				response = (PerceptionTcpResponse) obj;
				log.debug("服务器远程查询设备请求收到响应,响应帧序号为:[" + response.getSeq() + "]");
				session.removeAttribute(seq);// 不会清掉其他session的值
				perceptionCurOptMap.remove(machineAddr);
				return response;
			}
		}
	}

	public PerceptionTcpResponse remoteOperateMachine(Integer perceptionId, Integer opt) {
		Perception p = this.perceptionDao.get(Perception.class, perceptionId);
		String machineAddr = p.getPerceptionAddr();
		ClientConnector clientConnection = this.clientConnectionPool.getClientConnector(machineAddr);

		if (null == clientConnection) {
			log.debug("当前感知端已失去连接，可能已经离线  machineAddr=" + machineAddr);
			throw new GenericException("当前设备没有连接到服务器，请联系设备管理员");
		}

		PerceptionTcpResponse response = new PerceptionTcpResponse();

		// 判断当前感知端是否有操作，如果有，则返回
		if (perceptionCurOptMap.containsKey(machineAddr)) {
			response.setResult((byte) 0x03);
			return response;
		}

		final IoSession session = clientConnection.getIoSession();

		byte[] content = new byte[69];
		// 帧序列号
		content[0] = (byte) 0xA1;
		content[1] = 0x08;
		long seq = this.nextSeq();
		System.arraycopy(ByteUtils.longToByteArray(seq), 0, content, 2, 8);
		// 帧总长度
		content[10] = (byte) 0xA2;
		content[11] = 0x04;
		System.arraycopy(ByteUtils.intToByteArray(68), 0, content, 12, 4);
		// 业务类型
		content[16] = (byte) 0xA3;
		content[17] = 0x01;
		content[18] = 0x01;
		// CRC32校验和
		content[19] = (byte) 0xA4;
		content[20] = 0x08;
		// 设备类型
		content[29] = (byte) 0xA5;
		content[30] = 0x01;
		byte bytePerceptionTypeId = (byte) p.getPerceptionTypeId().intValue();
		content[31] = bytePerceptionTypeId;
		// 电机地址
		content[32] = (byte) 0xB1;
		content[33] = 0x20;
		System.arraycopy(machineAddr.getBytes(), 0, content, 34, machineAddr.getBytes().length);
		// 操作标识
		content[66] = (byte) 0xB2;
		content[67] = 0x01;
		content[68] = (byte) opt.intValue();
		perceptionCurOptMap.put(machineAddr, opt);

		long crc32l = ByteUtils.byteArrCRC32Value(content);
		System.arraycopy(ByteUtils.longToByteArray(crc32l), 0, content, 21, 8);

		final IoBuffer ib = IoBufferUtils.byteToIoBuffer(content);

		session.write(ib);

		long reqTime = System.currentTimeMillis();

		// 等待返回结果
		while (true) {

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 响应超时
			long nowTime = System.currentTimeMillis();
			long timeout = 7 * 1000;
			long count = nowTime - reqTime;
			if (count > timeout) {
				log.debug("远程控制电机超时:帧序号seq=" + seq);
				response.setResult((byte) 0x02);
				session.removeAttribute(seq);// 不会清掉其他session的值
				perceptionCurOptMap.remove(machineAddr);
				return response;
			}

			Object obj = session.getAttribute(seq);

			if (null != obj) {
				response = (PerceptionTcpResponse) obj;
				session.removeAttribute(seq);// 不会清掉其他session的值
				perceptionCurOptMap.remove(machineAddr);
				return response;
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
