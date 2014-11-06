package com.dahuangit.seobi.proxy.tcpserver.iofilter;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

/**
 * 解码器(接收来自客户端的信息)
 * 
 * @author 黄仁良
 * 
 */
public class ProxyServerDecoder extends CumulativeProtocolDecoder {

	private static final Logger log = Log4jUtils.getLogger(ProxyServerDecoder.class);

	/** 累积解码器 返回true表示接收包完毕，返回false mina会自动累数据 */
	protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		log.debug("服务器端在接收客户端数据之前先进行解码...");

		byte[] content = IoBufferUtils.ioBufferToByte(buffer);

		out.write(content);

		log.debug("服务器端解码完成");
		return true;
	}
}
