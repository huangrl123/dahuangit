package com.dahuangit.iots.perception.tcpclient;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.dahuangit.iots.perception.tcpclient.handler.PerceptionTcpClientHandler;
import com.dahuangit.iots.perception.tcpclient.iofilter.PerceptionClientCodesFactory;
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
		// ConnectFuture cf = connector.connect(new InetSocketAddress("120.24.86.107", 9999));

		log.debug("客户端已经启动!");

		// 等待连接创建完成
		cf.awaitUninterruptibly();

		String machineAddr = "huizhoueden";
		IoSession session = cf.getSession();

		byte[] content = new byte[100];
		// 帧序列号
		content[0] = (byte) 0xA1;
		content[1] = 0x08;
		long seq = 444l;
		System.arraycopy(ByteUtils.longToByteArray(seq), 0, content, 2, 8);
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
		byte bytePerceptionTypeId = 1;
		content[31] = bytePerceptionTypeId;
		// 电机地址
		content[32] = (byte) 0xB1;
		content[33] = 0x20;
		System.arraycopy(machineAddr.getBytes(), 0, content, 34, machineAddr.getBytes().length);
		// 操作标识
		content[66] = (byte) 0xB2;
		content[67] = 0x01;
		content[68] = 0x01;

		// 电机1旋转状态
		content[69] = (byte) 0xB3;
		content[70] = 0x01;
		content[71] = 0x01;

		// 电机1开关状态
		content[72] = (byte) 0xB4;
		content[73] = 0x01;
		content[74] = 0x01;

		// i2c状态
		content[75] = (byte) 0xB6;
		content[76] = 0x01;
		content[77] = 0x00;
		content[78] = 0x01;

		// 红外状态
		content[79] = (byte) 0xB7;
		content[80] = 0x01;
		content[81] = 0x01;

		// 电机2旋转状态
		content[91] = (byte) 0xBB;
		content[92] = 0x01;
		content[93] = 0x02;

		//电机2开关状态
		content[94] = (byte) 0xBC;
		content[95] = 0x01;
		content[96] = 0x01;
		
		//按键状态
		content[97] = (byte) 0xBD;
		content[98] = 0x01;
		content[99] = 0x02;
		
		long crc32l = ByteUtils.byteArrCRC32Value(content);
		System.arraycopy(ByteUtils.longToByteArray(crc32l), 0, content, 21, 8);

		IoBuffer ib = IoBufferUtils.byteToIoBuffer(content);

		session.write(ib);

		log.debug("2+2客户端模拟端向服务器端发送的数据content=" + ByteUtils.byteArrToHexString(content));

		session.getCloseFuture().awaitUninterruptibly();

		// 释放连接
		connector.dispose();
	}
}
