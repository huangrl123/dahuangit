package com.dahuangit.iots.perception.tcpserver.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.transport.socket.SocketSessionConfig;
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
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
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

		log.debug("服务器端收到客户端的信息，报文:" + ByteUtils.byteArrToHexString(content));

		byte[] temArr = null;

		long seq = 0l;
		int length = 0;
		byte busType = 0;
		long crc32 = 0;
		String machineAddr = null;
		byte operateFlag = 0;
		byte perceptionType = 0;

		try {
			temArr = new byte[8];
			System.arraycopy(content, 2, temArr, 0, 8);
			seq = ByteUtils.byteArrToLong(temArr);

			temArr = new byte[4];
			System.arraycopy(content, 12, temArr, 0, 4);
			length = ByteUtils.byteArrToInt(temArr);

			busType = content[18];

			temArr = new byte[8];
			System.arraycopy(content, 21, temArr, 0, 8);
			crc32 = ByteUtils.byteArrToLong(temArr);

			perceptionType = content[31];

			temArr = new byte[32];
			System.arraycopy(content, 34, temArr, 0, 32);
			machineAddr = new String(temArr);
			machineAddr = machineAddr.trim();

			operateFlag = content[68];
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("报文数组不正确", e);
		}

		switch (busType) {
		case (byte) 0x01: // 客户端的请求
			switch (operateFlag) {
			case 0x01:// 客户端上传电机状态
				try {
					if (perceptionType == 1) {// 2+2的请求
						Machine2j2SendStatusRequest request = new Machine2j2SendStatusRequest();
						byte rotateStatus = content[71];
						byte switchStatus = content[74];

						temArr = new byte[2];
						System.arraycopy(content, 77, temArr, 0, 2);
						byte[] i2cStatus = temArr;

						byte rotateStatus2 = content[93];

						request.setBusType(busType);
						request.setCrc32(crc32);
						request.setLength(length);
						request.setMachineAddr(machineAddr);
						request.setOperateFlag(operateFlag);
						request.setPerceptionType(perceptionType);
						request.setSeq(seq);

						request.setRotateStatus(rotateStatus);
						request.setSwitchStatus(switchStatus);
						request.setI2cStatus(i2cStatus);
						request.setRotateStatus2(rotateStatus2);
						perceptionService.saveLog(request);
					} else if (perceptionType == 2) {// 6+6的请求
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
					byte rotateStatus = content[71];
					byte switchStatus = content[74];
					byte rotateStatus2 = content[96];

					temArr = new byte[2];
					System.arraycopy(content, 80, temArr, 0, 2);
					byte[] i2cStatus = temArr;

					response.setBusType(busType);
					response.setCrc32(crc32);
					response.setLength(length);
					response.setMachineAddr(machineAddr);
					response.setOperateFlag(operateFlag);
					response.setPerceptionType(perceptionType);
					response.setSeq(seq);

					response.setRotateStatus(rotateStatus);
					response.setSwitchStatus(switchStatus);
					response.setI2cStatus(i2cStatus);
					response.setRotateStatus2(rotateStatus2);

					perceptionService.saveLog(response);

					// 将响应结果对象放到session上下文里
					session.setAttribute(seq, response);

				} else if (perceptionType == 2) {// 6+6的响应
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

					perceptionService.saveLog(response);

					// 将响应结果对象放到session上下文里
					session.setAttribute(seq, response);
				}
				break;

			case 0x03:// 服务器远程正转控制的响应
			case 0x04:// 服务器远程反转控制的响应
			case 0x05:// 服务器远程开控制的响应
			case 0x06:// 服务器远程关控制的响应
			case 0x07:// 服务器远程I2C开的响应
			case 0x08:// 服务器远程I2C关的响应
			case 0x09:// 服务器远程正转控制2
			case 0x0A:// 服务器远程反转控制2

				ServerCtrlMachineResponse response = new ServerCtrlMachineResponse();
				response.setBusType(busType);
				response.setCrc32(crc32);
				response.setLength(length);
				response.setMachineAddr(machineAddr);
				response.setOperateFlag(operateFlag);
				response.setPerceptionType(perceptionType);
				response.setSeq(seq);

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
		session.close();
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
