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

		byte[] data = (byte[]) message;
		log.debug("客户端收到报文:" + ByteUtils.byteArrToHexString(data));
		
		String machineAddr = "DSFE432EWR";
		
		// 如果不是心跳请求的响应，则需要给服务器端一个成功响应
		if (data[71] != 1) {
			byte[] content = new byte[72];
			// 帧序列号
			content[0] = (byte) 0xA1;
			content[1] = 0x08;
			System.arraycopy(data, 0, content, 2, 8);//非常重要，一定要用原来的帧序号返回
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
			byte bytePerceptionTypeId = 2;
			content[31] = bytePerceptionTypeId;
			// 电机地址
			content[32] = (byte) 0xB1;
			content[33] = 0x20;
			System.arraycopy(machineAddr.getBytes(), 0, content, 34, machineAddr.getBytes().length);
			// 操作标识
			content[66] = (byte) 0xB2;
			content[67] = 0x01;
			content[68] = 0x00;
			content[69] = 0x00;
			content[70] = 0x00;
			content[71] = 0x01; 
			//处理结果
		    content[72] = (byte) 0xB5;
		    content[73] = 0x01;
		    content[74] = 0x01;
		    
		    IoBuffer ioBuffer = IoBufferUtils.byteToIoBuffer(content);
			session.write(ioBuffer);
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
