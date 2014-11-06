package com.dahuangit.seobi.proxy.tcpclient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.dahuangit.seobi.proxy.tcpclient.handler.PerceptionTcpClientHandler;
import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

import constant.SeobiProxyTestConstants;

/**
 * 2+2客户端模拟端
 * 
 * @author 黄仁良
 * 
 */
public class ProxyClient {
	private static final Logger log = Log4jUtils.getLogger(SeobiProxyTestConstants.LOG4J_FILE_PATH, ProxyClient.class);

	public static void main(String[] args) {
		log.debug("正在启动客户端...");

		NioSocketConnector connector = new NioSocketConnector();

		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8")));
		log.debug("过滤器为:PrefixedStringCodecFactory");

		chain.addLast("objectFilter", filter);

		connector.setHandler(new PerceptionTcpClientHandler());

		// 设置链接超时时间
		connector.setConnectTimeoutCheckInterval(30);

		// 建立连接
		log.debug("客户端正在建立连接...");
		ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 9999));

		log.debug("客户端已经启动!");

		// 等待连接创建完成
		cf.awaitUninterruptibly();

		IoSession session = cf.getSession();

		log.debug("客户端开始向服务端发送数据...");
		String content = "CumulativeProtocolDecoder";
		session.write(IoBufferUtils.byteToIoBuffer(content.getBytes()));
		
		try {
			Thread.sleep(7 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		content = "test";
		session.write(IoBufferUtils.byteToIoBuffer(content.getBytes()));
		log.debug("客户端向服务端发送的数据为：" + content);
		session.getCloseFuture().awaitUninterruptibly();

		// 释放连接
		connector.dispose();
	}
}
