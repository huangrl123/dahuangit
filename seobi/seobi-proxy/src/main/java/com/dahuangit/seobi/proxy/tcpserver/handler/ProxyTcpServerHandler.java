package com.dahuangit.seobi.proxy.tcpserver.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.dahuangit.seobi.proxy.service.ProxyService;
import com.dahuangit.seobi.proxy.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpHeaderInfo;
import com.dahuangit.util.net.http.HttpHeaderParser;

/**
 * 处理类
 * 
 * @author 黄仁良
 * 
 */
public class ProxyTcpServerHandler implements IoHandler {

	private final static Logger log = Log4jUtils.getLogger(ProxyTcpServerHandler.class);

	/** 客户端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	@Autowired
	private ProxyService proxyService = null;

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
		// log.debug("服务器handler监听到发生异常,可能是因为客户端已经断开");
		// throwable.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] content = (byte[]) message;
		String msg = new String(content);
		if(!msg.contains("20140507.ip138.com/ic.asp")) {
			return;
		}
		
		log.debug("服务器收到客户端信息,msg=[" + msg + "]");

		log.debug("服务器开始通过代理请求地址...");
		HttpHeaderInfo headerInfo = HttpHeaderParser.parse(msg);
		String result = proxyService.doRequestByProxy(headerInfo);
		log.debug("服务器通过代理请求成功，响应结果为:" + result);

		session.write(IoBufferUtils.byteToIoBuffer(result.getBytes()));
		log.debug("服务器已将响应结果写给客户端");
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("服务器端向客户端发送的信息");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("客户端session已关闭");

		this.clientConnectionPool.removeClientConnectorBySessionId(session.getId());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("客户端session已创建");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("客户端session空闲 " + session.getIdleCount(status));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.debug("客户端session已打开");
		log.debug("客户端连接已经正式接入,客户端session.getId()=" + session.getId());
	}

}
