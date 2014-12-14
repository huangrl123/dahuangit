package com.dahuangit.iots.perception.tcpclient.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.log.Log4jUtils;

public class PerceptionTcpClientHandler extends IoHandlerAdapter {

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			PerceptionTcpClientHandler.class);

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("客户端session已经打开!");
		super.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.debug("session收到信息-messageReceived，session.getId()=" + session.getId());
		int currentSessionCount = session.getService().getManagedSessionCount();
		log.debug("当前被管理的session数量:" + currentSessionCount);

		byte[] content = (byte[]) message;

		log.debug("客户端收到报文:" + ByteUtils.byteArrToHexString(content));

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

			operateFlag = content[68];
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("报文数组不正确", e);
		}

		switch (busType) {
		case (byte) 0x01: // 服务端的请求
			log.debug("服务器端收到客户端的信息，报文:" + ByteUtils.byteArrToHexString(content));

			switch (operateFlag) {
			case 0x02:// 服务查询电机状态的请求
				byte[] respcontent = new byte[103];
				// 帧序列号
				respcontent[0] = (byte) 0xA1;
				respcontent[1] = 0x08;
				System.arraycopy(ByteUtils.longToByteArray(seq), 0, respcontent, 2, 8);
				// 帧总长度
				respcontent[10] = (byte) 0xA2;
				respcontent[11] = 0x04;
				System.arraycopy(ByteUtils.intToByteArray(68), 0, respcontent, 12, 4);
				// 业务类型
				respcontent[16] = (byte) 0xA3;
				respcontent[17] = 0x01;
				respcontent[18] = 0x02;
				// CRC32校验和
				respcontent[19] = (byte) 0xA4;
				respcontent[20] = 0x08;
				// 设备类型
				respcontent[29] = (byte) 0xA5;
				respcontent[30] = 0x01;
				byte bytePerceptionTypeId = 1;
				respcontent[31] = bytePerceptionTypeId;
				// 电机地址
				respcontent[32] = (byte) 0xB1;
				respcontent[33] = 0x20;
				System.arraycopy(machineAddr.getBytes(), 0, respcontent, 34, machineAddr.getBytes().length);
				// 操作标识
				respcontent[66] = (byte) 0xB2;
				respcontent[67] = 0x01;
				respcontent[68] = 0x02;

				// 电机1旋转状态
				respcontent[69] = (byte) 0xB3;
				respcontent[70] = 0x01;
				respcontent[71] = 0x01;

				// 电机1开关状态
				respcontent[72] = (byte) 0xB4;
				respcontent[73] = 0x01;
				respcontent[74] = 0x01;

				// 处理结果
				respcontent[75] = (byte) 0xB5;
				respcontent[76] = 0x01;
				respcontent[77] = 0x01;

				// i2c状态
				respcontent[78] = (byte) 0xB6;
				respcontent[79] = 0x01;
				respcontent[80] = 0x00;
				respcontent[81] = 0x01;

				// 红外状态
				respcontent[82] = (byte) 0xB7;
				respcontent[83] = 0x01;
				respcontent[84] = 0x02;

				// 电机2旋转状态
				respcontent[94] = (byte) 0xBB;
				respcontent[95] = 0x01;
				respcontent[96] = 0x02;

				// 电机2开关状态
				respcontent[97] = (byte) 0xBC;
				respcontent[98] = 0x01;
				respcontent[99] = 0x02;

				// 按键状态
				respcontent[100] = (byte) 0xBD;
				respcontent[101] = 0x01;
				respcontent[102] = 0x02;

				long crc32l = ByteUtils.byteArrCRC32Value(respcontent);
				System.arraycopy(ByteUtils.longToByteArray(crc32l), 0, respcontent, 21, 8);

				IoBuffer ib = IoBufferUtils.byteToIoBuffer(respcontent);

				session.write(ib);
				break;
			// 服务器远程控制的请求
			case 0x03:// 电机1正转控制
			case 0x04:// 电机1反转控制
			case 0x05:// 电机1开控制
			case 0x06:// 电机1关控制
			case 0x07:// I2C开
			case 0x08:// I2C关
			case 0x09:// 电机2正转控制
			case 0x0A:// 电机2反转控制
			case 0x0B:// 电机2通电控制
			case 0x0C:// 电机2断电控制
				// 帧序列号
				byte[] ctrlResponseContent = new byte[72];
				ctrlResponseContent[0] = (byte) 0xA1;
				ctrlResponseContent[1] = 0x08;
				System.arraycopy(ByteUtils.longToByteArray(seq), 0, ctrlResponseContent, 2, 8);

				// 帧总长度
				ctrlResponseContent[10] = (byte) 0xA2;
				ctrlResponseContent[11] = 0x04;
				System.arraycopy(ByteUtils.intToByteArray(72), 0, ctrlResponseContent, 12, 4);

				// 业务类型
				ctrlResponseContent[16] = (byte) 0xA3;
				ctrlResponseContent[17] = 0x01;
				ctrlResponseContent[18] = 0x02;

				// CRC32校验和
				ctrlResponseContent[19] = (byte) 0xA4;
				ctrlResponseContent[20] = 0x08;

				// 设备类型
				ctrlResponseContent[29] = (byte) 0xA5;
				ctrlResponseContent[30] = 0x01;
				ctrlResponseContent[31] = 0x01;

				// 电机地址
				ctrlResponseContent[32] = (byte) 0xB1;
				ctrlResponseContent[33] = 0x20;
				System.arraycopy(machineAddr.getBytes(), 0, ctrlResponseContent, 34, machineAddr.getBytes().length);

				// 操作标识
				ctrlResponseContent[66] = (byte) 0xB2;
				ctrlResponseContent[67] = 0x01;
				ctrlResponseContent[68] = operateFlag;

				// 处理结果
				ctrlResponseContent[69] = (byte) 0xB5;
				ctrlResponseContent[70] = 0x01;
				ctrlResponseContent[71] = 0x01;

				ib = IoBufferUtils.byteToIoBuffer(ctrlResponseContent);

				session.write(ib);
				break;
			default:
				throw new RuntimeException("报文数组不正确");
			}
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("客户端session已经关闭!");
		super.sessionClosed(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.debug("客户端session发生异常!");
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("客户端session已经向服务端发送请求!");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("客户端session已经创建!");
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("客户端session已经空闲!");
		super.sessionIdle(session, status);
	}

}
