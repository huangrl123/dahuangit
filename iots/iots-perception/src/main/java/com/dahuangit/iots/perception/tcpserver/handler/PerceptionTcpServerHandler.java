package com.dahuangit.iots.perception.tcpserver.handler;

import java.util.Date;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.iots.perception.constant.KeyConstants;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.service.PerceptionService;
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
@Transactional
public class PerceptionTcpServerHandler implements IoHandler {

	private final static Logger log = Log4jUtils.getLogger(PerceptionTcpServerHandler.class);

	/** 客户端连接池 */
	private ClientConnectorPool clientConnectionPool = ClientConnectorPool.getInstance();

	/** 客户端响应池 */
	private ClientResponsePool clientResponsePool = ClientResponsePool.getInstance();

	@Autowired
	private PerceptionService perceptionService = null;

	@Autowired
	private PerceptionDao perceptionDao = null;

	@Autowired
	private ExecutorService executorService = null;
	
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
			String addr = frame.getMachineAddr();
			clientConnect.setClientKey(addr);
			clientConnect.setLastCommTime(System.currentTimeMillis());
			clientConnect.setIoSession(session);

			this.clientConnectionPool.addClientConnector(frame.getMachineAddr(), clientConnect);

			newContent = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);

			needResponse = true;
			
			saveLog(frame);
			break;

		case (byte) 0x02:// 客户端的响应
			log.debug("服务器端收到客户端的响应信息，报文:" + ByteUtils.byteArrToHexString(content));

			// 判断所有参数只要不为0就记录日志
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

	private void saveLog(final PerceptionFrame frame) {
		Thread t = new Thread() {

			@Override
			public void run() {
				// 处理请求并响应客户端
				frame.setBusType((byte) 0x02);
				frame.setResult((byte) 0x01);

				String addr = frame.getMachineAddr();

				// 判断该感知端是否已经在系统中有记录
				Perception p = perceptionDao.findUniqueBy("perceptionAddr", addr);
				if (null != p) {
					p.setLastCommTime(new Date());
					perceptionDao.update(p);
				} else {
					p = new Perception();
					p.setCreateDateTime(new Date());
					p.setInstallSite("测试环境");
					p.setLastCommTime(new Date());
					p.setPerceptionAddr(addr);
					p.setPerceptionName("测试设备");
					p.setPerceptionTypeId(1);// 2+2
					perceptionDao.add(p);
				}

				// 记录该感知端的参数日志
				//开关
				perceptionService.addPerceptionRuntimeLog(addr, 3, 1, frame.getSwitchStatus());
				//旋转
				perceptionService.addPerceptionRuntimeLog(addr, 5, 1, frame.getRotateStatus());
			}

		};

		this.executorService.execute(t);
	}
}
