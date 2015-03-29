package com.dahuangit.iots.perception.tcpserver.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine2j2SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerCtrlMachineResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.processor.impl.PerceptionProcessorImpl;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.log.Log4jUtils;

/**
 * 处理类
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionTcpServerHandler implements IoHandler {

	private final static Logger log = Log4jUtils.getLogger(PerceptionTcpServerHandler.class);

	/** 客户端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	@Autowired
	private PerceptionService perceptionService = null;

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private SessionFactory sessionFactory = null;

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) {
	}

	/**
	 * 接收来自客户端的消息
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {

		byte[] content = (byte[]) message;

		if (content.length != 72 && content.length != 69 && content.length != 75 && content.length != 100) {
			log.debug("发生错误，报文长度不对, 正确的长度应为69或者72或者75或者100, 实际报文长度为:" + content.length);
			session.close(true);
			throw new RuntimeException("发生错误，报文长度不对");
		}

		log.debug("收到客户端发过来的报文,报文内容为:");

		byte[] temArr = null;

		long seq = 0l;
		int length = 0;
		int busType = 0;
		long crc32 = 0;
		String machineAddr = null;
		int operateFlag = 0;
		byte perceptionType = 0;

		String hex = null;

		try {
			String errorMsg = null;

			// 帧序列号
			if (content[0] != (byte) 0xA1) {
				errorMsg = "未找到帧序列号标识，content[0] != (byte)0xA1";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			temArr = new byte[8];
			System.arraycopy(content, 2, temArr, 0, 8);
			seq = ByteUtils.byteArrToLong(temArr);

			// 帧总长度
			if (content[10] != (byte) 0xA2) {
				errorMsg = "未找到帧总长度标识，content[10] != (byte)0xA2";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			temArr = new byte[4];
			System.arraycopy(content, 12, temArr, 0, 4);
			length = ByteUtils.byteArrToInt(temArr);

			// 业务类型
			if (content[16] != (byte) 0xA3) {
				errorMsg = "未找到业务类型标识，content[16] != (byte)0xA3";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			busType = content[18];

			// CRC32校验和
			if (content[19] != (byte) 0xA4) {
				errorMsg = "未找到CRC32校验和标识，content[19] != (byte)0xA4";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			temArr = new byte[8];
			System.arraycopy(content, 21, temArr, 0, 8);
			crc32 = ByteUtils.byteArrToLong(temArr);

			// 设备类型
			if (content[29] != (byte) 0xA5) {
				errorMsg = "未找到设备类型标识，content[29] != (byte)0xA5";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			perceptionType = content[31];

			// 电机地址
			if (content[32] != (byte) 0xB1) {
				errorMsg = "未找到电机地址标识，content[32] != (byte)0xB1";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			temArr = new byte[32];
			System.arraycopy(content, 34, temArr, 0, 32);
			machineAddr = new String(temArr);
			machineAddr = machineAddr.trim();

			session.setAttribute("machineAddr", machineAddr);

			// 操作标识
			if (content[66] != (byte) 0xB2) {
				errorMsg = "未找到操作标识标识，content[66] != (byte)0xB2";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}

			// 2+2
			if (perceptionType == 1) {
				operateFlag = content[68];
			}

			// 其他类型的设备
			else {
				temArr = new byte[4];
				System.arraycopy(content, 68, temArr, 0, 4);
				operateFlag = ByteUtils.byteArrToInt(temArr);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("报文数组不正确", e);
		}

		Machine2j2SendStatusRequest request = null;

		switch (busType) {
		case (byte) 0x01: // 客户端的请求
			if (operateFlag == 1) {
				// 如果是2+2设备则表示客户端上传电机状态，其他设备表示心跳
				try {
					// 响应设备
					byte[] responseContent = null;

					// 2+2的设备
					if (perceptionType == 1) {

						// 长度等于69为2+2心跳操作
						if (content.length != 69) {
							if (content.length != 100) {
								String errorMsg = "2+2上报状态请求报文内容长度错误：正确的长度是100，实际长度为" + content.length;
								log.debug(errorMsg);
								throw new RuntimeException(errorMsg);
							}

							hex = ByteUtils.byteArrToHexString(content);
							log.debug("服务器端收到客户端的信息[2+2客户端上传电机状态],帧序号为:[" + seq + "]");
							log.debug("服务器端收到客户端的信息[2+2客户端上传电机状态]，报文:" + hex);
							log.debug("帧序号seq=" + seq);

							byte machine1RotateStatus = content[71];
							byte machine1SwitchStatus = content[74];

							temArr = new byte[2];
							System.arraycopy(content, 77, temArr, 0, 2);
							byte[] i2cStatus = temArr;

							byte infraredStatus = content[81];

							byte machine2RotateStatus = content[93];
							byte machine2SwitchStatus = content[96];
							byte pressKeyStatus = content[99];

							request = new Machine2j2SendStatusRequest();

							request.setBusType(busType);
							request.setCrc32(crc32);
							request.setLength(length);
							request.setMachineAddr(machineAddr);
							request.setOperateFlag(operateFlag);
							request.setPerceptionType(perceptionType);
							request.setSeq(seq);

							// 如果电机1的开关状态不是0，则将0x03赋值给电机1的旋转状态
							if (machine1SwitchStatus != (byte) 0x00) {
								request.setMachine1RotateStatus((byte) 0x03);
							} else {
								request.setMachine1RotateStatus(machine1RotateStatus);
							}

							// 如果电机2的开关状态不是0，则将0x03赋值给电机2的旋转状态
							if (machine2SwitchStatus != (byte) 0x00) {
								request.setMachine2RotateStatus((byte) 0x03);
							} else {
								request.setMachine2RotateStatus(machine2RotateStatus);
							}

							request.setPressKeyStatus(pressKeyStatus);
							request.setInfraredStatus(infraredStatus);
							request.setI2cStatus(i2cStatus);
							request.setHex(hex);
							perceptionService.saveLog(request);

							responseContent = new byte[72];
							System.arraycopy(content, 0, responseContent, 0, 69);
							responseContent[69] = (byte) 0xB5;
							responseContent[70] = 0x01;
							responseContent[71] = 0x01;// 结果为成功
						}
					}

					// 除了2+2设备外的其他设备的请求
					else {
						PerceptionTcpDto perceptionTcpDto = new PerceptionTcpDto();
						perceptionTcpDto.setBusType(busType);
						perceptionTcpDto.setCrc32(crc32);
						perceptionTcpDto.setHex(hex);
						perceptionTcpDto.setLength(length);
						perceptionTcpDto.setMachineAddr(machineAddr);
						perceptionTcpDto.setOperateFlag(operateFlag);
						perceptionTcpDto.setPerceptionType(perceptionType);
						perceptionTcpDto.setSeq(seq);
						perceptionService.saveLog(perceptionTcpDto);

						responseContent = new byte[75];
						System.arraycopy(content, 0, responseContent, 0, 66);
						responseContent[66] = (byte) 0xB2;
						responseContent[67] = 0x04;
						responseContent[68] = 0x00;
						responseContent[69] = 0x00;
						responseContent[70] = 0x00;
						responseContent[71] = 0x01;
						responseContent[72] = (byte) 0xB5;
						responseContent[73] = 0x01;
						responseContent[74] = 0x01;
					}

					responseContent[18] = 0x02;// 设置为响应类型

					IoBuffer ioBuffer = IoBufferUtils.byteToIoBuffer(responseContent);
					session.write(ioBuffer);

				} catch (Exception e1) {
					e1.printStackTrace();
					throw new RuntimeException("报文数组不正确", e1);
				}
			}

			// 将当前客户端会话放入会话池
			ClientConnector clientConnect = new ClientConnector();
			clientConnect.setClientKey(machineAddr);
			clientConnect.setIoSession(session);
			this.clientConnectionPool.addClientConnector(machineAddr, clientConnect);

			// 如果2+2的请求不为null，则放入缓存
			if (null != request) {
				Map<Integer, Integer> currentStatus = this.clientConnectionPool.getClientConnector(machineAddr)
						.getCurrentStatus();
				// 电机1旋转状态
				currentStatus.put(179, (int) request.getMachine1RotateStatus());
				// I2C状态
				currentStatus.put(182, ByteUtils.byteArrToInt(request.getI2cStatus()));
				// 红外状态
				currentStatus.put(183, (int) request.getInfraredStatus());
				// 电机2旋转状态
				currentStatus.put(187, (int) request.getMachine2RotateStatus());
				// 按键状态
				currentStatus.put(189, (int) request.getPressKeyStatus());
			}
			break;

		case (byte) 0x02:// 远程控制的响应
			ServerCtrlMachineResponse response = new ServerCtrlMachineResponse();

			log.debug("帧序号seq=" + seq);

			byte result = 0;

			if (perceptionType == 1) {
				result = content[71];
			}

			else {
				result = content[74];
			}

			response.setBusType(busType);
			response.setCrc32(crc32);
			response.setLength(length);
			response.setMachineAddr(machineAddr);
			response.setOperateFlag(operateFlag);
			response.setPerceptionType(perceptionType);
			response.setSeq(seq);

			response.setHex(hex);

			response.setResult(result);

			// 将响应结果对象放到session上下文里
			session.setAttribute(seq, response);

			break;
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String machineAddr = (String) session.getAttribute("machineAddr");
		PerceptionProcessorImpl.perceptionCurOptMap.remove(machineAddr);
		clientConnectionPool.removeClientConnector(machineAddr);
		// 判断该感知端是否已经在系统中有记录
		session.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

}
