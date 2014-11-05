package com.dahuangit.iots.perception.tcpclient.iofilter;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

/**
 * 编码器
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionClientEncoder extends ProtocolEncoderAdapter {

	private static final Logger LOG = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties",
			PerceptionClientEncoder.class);

	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		output.write(IoBufferUtils.byteToIoBuffer((byte[]) object));
	}
}
