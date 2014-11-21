package com.dahuangit.iots.perception.tcpclient;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.dahuangit.iots.perception.tcpclient.handler.PerceptionTcpClientHandler;
import com.dahuangit.iots.perception.tcpclient.iofilter.PerceptionClientCodesFactory;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrameConvertor;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.log.Log4jUtils;

/**
 * 2+2客户端模拟端
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionClient {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			PerceptionClient.class);

	public static void main(String[] args) {
		log.debug("正在启动客户端...");

		NioSocketConnector connector = new NioSocketConnector();

		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new PerceptionClientCodesFactory());
		chain.addLast("objectFilter", filter);

		connector.setHandler(new PerceptionTcpClientHandler());

		// 设置链接超时时间
		connector.setConnectTimeoutCheckInterval(30);

		// 建立连接
		ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 9999));
//		ConnectFuture cf = connector.connect(new InetSocketAddress("120.24.86.107", 9999));

		log.debug("客户端已经启动!");

		// 等待连接创建完成
		cf.awaitUninterruptibly();

		IoSession session = cf.getSession();

		PerceptionFrame frame = new PerceptionFrame();
		// 帧序列号
		frame.setSeq(567657l);
		// 帧总长度
		frame.setLength(66);
		// 业务类型
		frame.setBusType((byte) 0x01);
		// CRC32校验和
		frame.setCrc32(4343l);
		// 电机地址
		frame.setMachineAddr("DSFE432EWR");
		// 操作标识
		frame.setOperateFlag((byte) 0x01);
		// 旋转状态
		frame.setRotateStatus((byte) 0x01);
		// 开关状态
		frame.setSwitchStatus((byte) 0x01);

		byte[] content = PerceptionFrameConvertor.PerceptionFrameToByteArray(frame);
		log.debug("2+2客户端模拟端向服务器端发送的数据content=" + ByteUtils.byteArrToHexString(content));

		session.write(IoBufferUtils.byteToIoBuffer(content));

		session.getCloseFuture().awaitUninterruptibly();

		// 释放连接
		connector.dispose();
	}
}
