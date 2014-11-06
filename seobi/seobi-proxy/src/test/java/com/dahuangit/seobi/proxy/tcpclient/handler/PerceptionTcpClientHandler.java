package com.dahuangit.seobi.proxy.tcpclient.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.dahuangit.util.log.Log4jUtils;

import constant.SeobiProxyTestConstants;

public class PerceptionTcpClientHandler extends IoHandlerAdapter {

	private static final Logger log = Log4jUtils.getLogger(SeobiProxyTestConstants.LOG4J_FILE_PATH,
			PerceptionTcpClientHandler.class);

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("客户端session已经打开!");
		super.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
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
