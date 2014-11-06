package com.dahuangit.seobi.proxy.tcpclient.iofilter;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

import constant.SeobiProxyTestConstants;

/**
 * 解码器
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionClientDecoder extends CumulativeProtocolDecoder {

	private static final Logger LOG = Log4jUtils.getLogger(SeobiProxyTestConstants.LOG4J_FILE_PATH,
			PerceptionClientDecoder.class);

	/** 累积解码器 返回true表示接收包完毕，返回false mina会自动累数据 */
	protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		byte[] content = IoBufferUtils.ioBufferToByte(buffer);

		out.write(content);

		out.write(buffer);
		return false;
	}

}
