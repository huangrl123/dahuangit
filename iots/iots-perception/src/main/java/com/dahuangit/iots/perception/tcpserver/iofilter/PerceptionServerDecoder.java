package com.dahuangit.iots.perception.tcpserver.iofilter;

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
public class PerceptionServerDecoder extends CumulativeProtocolDecoder {

	private static final Logger log = Log4jUtils.getLogger(PerceptionServerDecoder.class);

	/** 累积解码器 返回true表示接收包完毕，返回false mina会自动累数据 */
	protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		byte[] content = IoBufferUtils.ioBufferToByte(buffer);

		out.write(content);
		
		return true;
	}
}
