package com.dahuangit.iots.perception.tcpserver.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dahuangit.iots.perception.service.PerceptionService;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine2j2SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.request.Machine6j6SendStatusRequest;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerCtrlMachineResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine2j2StatusResponse;
import com.dahuangit.iots.perception.tcpserver.dto.response.ServerQueryMachine6j6StatusResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
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
	private SessionFactory sessionFactory = null;

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) {
		// log.debug("服务器handler监听到发生异常,可能是因为客户端已经断开");

		// throwable.printStackTrace();
	}

	/**
	 * 接收来自客户端的消息
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.debug("session收到信息-messageReceived，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);

		byte[] content = (byte[]) message;

		if (content.length < 72) {
			log.debug("发生错误，原因报文总长度小于72");
			throw new RuntimeException("报文总长度不能小于72");
		}

		byte[] temArr = null;

		long seq = 0l;
		int length = 0;
		byte busType = 0;
		long crc32 = 0;
		String machineAddr = null;
		byte operateFlag = 0;
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

			// 操作标识
			if (content[66] != (byte) 0xB2) {
				errorMsg = "未找到操作标识标识，content[66] != (byte)0xB2";
				log.debug(errorMsg);
				throw new RuntimeException(errorMsg);
			}
			operateFlag = content[68];
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("报文数组不正确", e);
		}

		boolean flag = true;

		switch (busType) {
		case (byte) 0x01: // 客户端的请求
			switch (operateFlag) {
			case 0x01:// 客户端上传电机状态
				try {
					if (perceptionType == 1) {// 2+2的请求
						if (content.length != 100) {
							String errorMsg = "2+2上报状态请求报文内容长度错误：正确的长度是100，实际长度为" + content.length;
							log.debug(errorMsg);
							throw new RuntimeException(errorMsg);
						}

						hex = ByteUtils.byteArrToHexString(content);
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

						Machine2j2SendStatusRequest request = new Machine2j2SendStatusRequest();

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
					} else if (perceptionType == 2) {// 6+6的请求
						hex = ByteUtils.byteArrToHexString(content);
						log.debug("服务器端收到客户端的信息[6+6客户端上传电机状态]，报文:" + hex);
						Machine6j6SendStatusRequest request = new Machine6j6SendStatusRequest();
						byte rotateStatus = content[71];
						byte switchStatus = content[74];

						temArr = new byte[2];
						System.arraycopy(content, 77, temArr, 0, 2);
						byte[] i2cStatus = temArr;

						byte infraredStatus = content[81];
						byte vibrateStatus = content[84];
						byte pressureStatus = content[87];
						byte approachStatus = content[90];
						byte rotateStatus2 = content[93];

						request.setBusType(busType);
						request.setCrc32(crc32);
						request.setLength(length);
						request.setMachineAddr(machineAddr);
						request.setOperateFlag(operateFlag);
						request.setPerceptionType(perceptionType);
						request.setSeq(seq);
						request.setApproachStatus(approachStatus);
						request.setI2cStatus(i2cStatus);
						request.setInfraredStatus(infraredStatus);
						request.setPressureStatus(pressureStatus);
						request.setRotateStatus(rotateStatus);
						request.setRotateStatus2(rotateStatus2);
						request.setSwitchStatus(switchStatus);
						request.setVibrateStatus(vibrateStatus);

						request.setHex(hex);

						perceptionService.saveLog(request);
					}

					// 同一响应
					byte[] responseContent = new byte[72];
					System.arraycopy(content, 0, responseContent, 0, 69);
					responseContent[18] = 0x02;// 设置为响应类型
					responseContent[71] = 0x01;// 结果为成功

					IoBuffer ioBuffer = IoBufferUtils.byteToIoBuffer(responseContent);
					session.write(ioBuffer);

				} catch (Exception e1) {
					e1.printStackTrace();
					throw new RuntimeException("报文数组不正确", e1);
				}
				break;
			default:
				throw new RuntimeException("报文数组不正确");
			}

			// 将当前客户端会话放入会话池
			ClientConnector clientConnect = new ClientConnector();
			clientConnect.setClientKey(machineAddr);
			clientConnect.setLastCommTime(System.currentTimeMillis());
			clientConnect.setIoSession(session);
			this.clientConnectionPool.addClientConnector(machineAddr, clientConnect);

			break;

		case (byte) 0x02:// 客户端的响应
			switch (operateFlag) {
			case 0x02:// 服务器向电机查询状态的响应
				if (perceptionType == 1) {// 2+2的响应
					ServerQueryMachine2j2StatusResponse response = new ServerQueryMachine2j2StatusResponse();
					
					if (content.length != 103) {
						String errorMsg = "2+2服务器向电机查询状态的响应报文内容长度错误：正确的长度是103，实际长度为" + content.length;
						log.debug(errorMsg);
						// 将响应结果对象放到session上下文里
						response.setResult((byte) 0);
						session.setAttribute(seq, response);
						throw new RuntimeException(errorMsg);
					}

					hex = ByteUtils.byteArrToHexString(content);
					log.debug("服务器端收到客户端的信息[服务器向2+2电机查询状态的响应]，报文:" + hex);
					log.debug("帧序号seq=" + seq);

					byte machine1RotateStatus = content[71];
					byte machine1SwitchStatus = content[74];

					byte result = content[77];

					temArr = new byte[2];
					System.arraycopy(content, 80, temArr, 0, 2);
					byte[] i2cStatus = temArr;

					byte infraredStatus = content[84];

					byte machine2RotateStatus = content[96];
					byte machine2SwitchStatus = content[99];

					byte pressKeyStatus = content[102];

					response.setBusType(busType);
					response.setCrc32(crc32);
					response.setLength(length);
					response.setMachineAddr(machineAddr);
					response.setOperateFlag(operateFlag);
					response.setPerceptionType(perceptionType);
					response.setSeq(seq);

					// 如果电机1的开关状态不是0，则将0x03赋值给电机1的旋转状态
					if (machine1SwitchStatus != (byte) 0x00) {
						response.setMachine1RotateStatus((byte) 0x03);
					} else {
						response.setMachine1RotateStatus(machine1RotateStatus);
					}

					// 如果电机2的开关状态不是0，则将0x03赋值给电机2的旋转状态
					if (machine2SwitchStatus != (byte) 0x00) {
						response.setMachine2RotateStatus((byte) 0x03);
					} else {
						response.setMachine2RotateStatus(machine2RotateStatus);
					}

					response.setResult(result);
					response.setMachine1SwitchStatus(machine1SwitchStatus);
					response.setMachine2SwitchStatus(machine2SwitchStatus);
					response.setPressKeyStatus(pressKeyStatus);
					response.setInfraredStatus(infraredStatus);
					response.setI2cStatus(i2cStatus);

					response.setHex(hex);
					perceptionService.saveLog(response);

					// 将响应结果对象放到session上下文里
					session.setAttribute(seq, response);

				} else if (perceptionType == 2) {// 6+6的响应
					hex = ByteUtils.byteArrToHexString(content);
					log.debug("服务器端收到客户端的信息[服务器向6+6电机查询状态的响应]，报文:" + hex);
					log.debug("帧序号seq=" + seq);

					ServerQueryMachine6j6StatusResponse response = new ServerQueryMachine6j6StatusResponse();
					byte rotateStatus = content[71];
					byte switchStatus = content[74];

					temArr = new byte[2];
					System.arraycopy(content, 80, temArr, 0, 2);
					byte[] i2cStatus = temArr;

					byte infraredStatus = content[81];
					byte vibrateStatus = content[84];
					byte pressureStatus = content[87];
					byte approachStatus = content[90];
					byte rotateStatus2 = content[93];

					response.setBusType(busType);
					response.setCrc32(crc32);
					response.setLength(length);
					response.setMachineAddr(machineAddr);
					response.setOperateFlag(operateFlag);
					response.setPerceptionType(perceptionType);
					response.setSeq(seq);
					response.setApproachStatus(approachStatus);
					response.setI2cStatus(i2cStatus);
					response.setInfraredStatus(infraredStatus);
					response.setPressureStatus(pressureStatus);
					response.setRotateStatus(rotateStatus);
					response.setRotateStatus2(rotateStatus2);
					response.setSwitchStatus(switchStatus);
					response.setVibrateStatus(vibrateStatus);

					response.setHex(hex);

					perceptionService.saveLog(response);

					// 将响应结果对象放到session上下文里
					session.setAttribute(seq, response);
				}
				break;

			case 0x03:// 服务器远程电机1正转控制的响应
				log.debug("服务器端收到客户端的信息[服务器远程电机1正转控制的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				flag = false;
			case 0x04:// 服务器远程电机1反转控制的响应
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程电机1反转控制的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x05:// 服务器远程电机1开控制的响应
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程电机1开控制的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x06:// 服务器远程电机1关控制的响应
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程电机1关控制的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x07:// 服务器远程I2C开的响应
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程I2C开的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x08:// 服务器远程I2C关的响应
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程I2C关的响应]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x09:// 服务器远程控制电机2正转控制
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程控制电机2正转控制]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x0A:// 服务器远程控制电机2反转控制
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程控制电机2反转控制]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x0B:// 服务器远程控制电机2通电控制
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程控制电机2通电控制]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;
			case 0x0C:// 服务器远程控制电机2断电控制
				if (flag) {
					log.debug("服务器端收到客户端的信息[服务器远程控制电机2断电控制]，报文:" + ByteUtils.byteArrToHexString(content));
				}
				flag = false;

				ServerCtrlMachineResponse response = new ServerCtrlMachineResponse();
				
				if (content.length != 72) {
					String errorMsg = "2+2服务器远程控制设备的响应报文内容长度错误：正确的长度是72，实际长度为" + content.length;
					log.debug(errorMsg);
					response.setResult((byte) 0);
					session.setAttribute(seq, response);
					throw new RuntimeException(errorMsg);
				}

				log.debug("帧序号seq=" + seq);
				
				byte result = content[71];

				response.setBusType(busType);
				response.setCrc32(crc32);
				response.setLength(length);
				response.setMachineAddr(machineAddr);
				response.setOperateFlag(operateFlag);
				response.setPerceptionType(perceptionType);
				response.setSeq(seq);

				response.setHex(hex);

				response.setResult(result);

				perceptionService.saveLog(response);

				// 将响应结果对象放到session上下文里
				session.setAttribute(seq, response);
				break;

			}

			break;
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("session发送信息-messageSent，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);
		log.debug("服务器端向客户端发送的信息");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("session关闭-sessionClosed，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);

		this.clientConnectionPool.removeClientConnectorBySessionId(session.getId());
		session.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("session创建-sessionCreated，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("session空闲-sessionIdle，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.debug("session打开-sessionOpened，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);
	}

}
