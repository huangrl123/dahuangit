package com.dahuangit.iots.perception.tcpserver.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.dahuangit.iots.perception.constant.KeyConstants;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrameConvertor;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnector;
import com.dahuangit.iots.perception.tcpserver.pool.ClientConnectorPool;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponsePool;
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

	/** 客户端响应池 */
	private ClientResponsePool clientResponsePool = ClientResponsePool.getInstance();

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
		// log.debug("服务器handler监听到发生异常,可能是因为客户端已经断开");
		// throwable.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] content = (byte[]) message;

		PerceptionFrame frame = PerceptionFrameConvertor.ByteArrToPerceptionFrame(content);

		log.debug("服务器端收到来自客户端的信息...");

		log.debug("传递方向frame.getBusType()=" + ByteUtils.byteToHexString(frame.getBusType()));

		log.debug("操作标识frame.getOperateFlag()=" + ByteUtils.byteToHexString(frame.getOperateFlag()));

		boolean needResponse = false;
		byte[] newContent = null;

		switch (frame.getBusType()) {
		case (byte) 0x01: // 客户端的请求
			log.debug("服务器端收到客户端的信息，报文:" + ByteUtils.byteArrToHexString(content));

			ClientConnector clientConnect = new ClientConnector();
			clientConnect.setClientKey(frame.getMachineAddr());
			clientConnect.setLastCommTime(System.currentTimeMillis());
			clientConnect.setIoSession(session);

			this.clientConnectionPool.addClientConnector(frame.getMachineAddr(), clientConnect);

			// 处理请求并响应客户端
			frame.setBusType((byte) 0x02);
			frame.setResult((byte) 0x01);

			newContent = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);

			needResponse = true;

			break;

		case (byte) 0x02:// 客户端的响应
			log.debug("服务器端收到客户端的响应信息，报文:" + ByteUtils.byteArrToHexString(content));

			ClientResponse clientResponse = new ClientResponse();
			clientResponse.setSeq(frame.getSeq());
			clientResponse.setUpTime(System.currentTimeMillis());
			clientResponse.setPerceptionFrame(frame);
			// clientResponse.setIoSession(session);

			// 处理请求并响应客户端
			frame.setBusType((byte) 0x02);
			frame.setResult((byte) 0x01);

			// 将响应结果对象放到session上下文里
			session.setAttribute(KeyConstants.MINA_SESSION_RESPONSE_KEY, clientResponse);
			break;
		}

		if (needResponse) {
			log.debug("服务器端响应客户端，报文:" + ByteUtils.byteArrToHexString(newContent));
			IoBuffer ioBuffer = IoBufferUtils.byteToIoBuffer(newContent);
			session.write(ioBuffer);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("服务器端向客户端发送的信息");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("客户端session已关闭");

		this.clientConnectionPool.removeClientConnectorBySessionId(session.getId());
		this.clientResponsePool.removeClientResponseBySessionId((session.getId()));
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
		log.debug("=============================================================");
	}

}
