package com.dahuangit.iots.perception.tcpclient.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrameConvertor;
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
		byte[] content = (byte[]) message;

		PerceptionFrame frame = PerceptionFrameConvertor.ByteArrToPerceptionFrame(content);

		log.debug("客户端收到服务器信息...");

		log.debug("传递方向frame.getBusType()=" + ByteUtils.byteToHexString(frame.getBusType()));

		log.debug("操作标识frame.getOperateFlag()=" + ByteUtils.byteToHexString(frame.getOperateFlag()));

		boolean needResp = false;

		switch (frame.getBusType()) {
		case (byte) 0x01:// 服务器端的请求
			if (frame.getOperateFlag() == (byte) 0x02) {// 服务器端向客户端查询电机状态
				log.debug("客户端收到服务器的请求:服务器端向客户端查询电机状态");
				log.debug("服务器传过来的报文content=" + ByteUtils.byteArrToHexString(content));

				frame.setMachineAddr("DSFE432EWR");
				frame.setBusType((byte) 0x02);
				frame.setRotateStatus((byte) 0x01);
				frame.setSwitchStatus((byte) 0x02);
				frame.setResult((byte) 0x01);
			} else if ((frame.getOperateFlag() == (byte) 0x03) || (frame.getOperateFlag() == (byte) 0x04)
					|| (frame.getOperateFlag() == (byte) 0x05) || (frame.getOperateFlag() == (byte) 0x06)) {// 服务器远程控制电机
				log.debug("客户端收到服务器的请求:服务器远程控制电机,控制标识为:" + frame.getOperateFlag());
				log.debug("服务器传过来的报文content=" + ByteUtils.byteArrToHexString(content));

				frame.setMachineAddr("DSFE432EWR");
				frame.setBusType((byte) 0x02);
				frame.setResult((byte) 0x01);
			}

			needResp = true;
			break;

		case (byte) 0x02:// 服务器端的响应
			log.debug("客户端收到服务器端的响应:操作标识为:" + frame.getOperateFlag());
			log.debug("服务器传过来的报文content=" + ByteUtils.byteArrToHexString(content));
			break;
		}

		if (needResp) {
			byte[] newContent = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);

			IoBuffer ib = IoBufferUtils.byteToIoBuffer(newContent);

			session.write(ib);
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
