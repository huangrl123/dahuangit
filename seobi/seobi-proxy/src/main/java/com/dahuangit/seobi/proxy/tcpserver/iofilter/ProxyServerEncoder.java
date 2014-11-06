package com.dahuangit.seobi.proxy.tcpserver.iofilter;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.dahuangit.util.log.Log4jUtils;

/**
 * 编码器
 * 
 * @author 黄仁良
 * 
 */
public class ProxyServerEncoder extends ProtocolEncoderAdapter {
	
	private static final Logger log = Log4jUtils.getLogger(ProxyServerEncoder.class);

	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		log.debug("服务器端编码器开始执行...");
		
		output.write(stringToIoBuffer((String) object));
		
		log.debug("编码完成,服务器端编码器执行完毕!");
	}

	/**
	 * 将bytebuffer转换成string
	 * 
	 * @param str
	 */
	public static IoBuffer stringToIoBuffer(String str) {

		byte bt[] = str.getBytes();

		IoBuffer ioBuffer = IoBuffer.allocate(bt.length);
		ioBuffer.put(bt, 0, bt.length);
		ioBuffer.flip();
		return ioBuffer;
	}

}
