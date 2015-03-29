package com.dahuangit.iots.perception.tcpserver.iofilter;

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
public class PerceptionServerEncoder extends ProtocolEncoderAdapter {
	
	private static final Logger log = Log4jUtils.getLogger(PerceptionServerEncoder.class);

	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		output.write(stringToIoBuffer((String) object));
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
