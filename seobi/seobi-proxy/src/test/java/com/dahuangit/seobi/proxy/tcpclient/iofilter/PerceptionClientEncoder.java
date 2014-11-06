package com.dahuangit.seobi.proxy.tcpclient.iofilter;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.dahuangit.util.IoBufferUtils;
import com.dahuangit.util.log.Log4jUtils;

import constant.SeobiProxyTestConstants;

/**
 * 编码器
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionClientEncoder extends ProtocolEncoderAdapter {

	private static final Logger LOG = Log4jUtils.getLogger(SeobiProxyTestConstants.LOG4J_FILE_PATH,
			PerceptionClientEncoder.class);

	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		output.write(IoBufferUtils.byteToIoBuffer((byte[]) object));
	}
}
