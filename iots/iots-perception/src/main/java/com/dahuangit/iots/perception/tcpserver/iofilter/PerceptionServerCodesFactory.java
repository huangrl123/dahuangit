package com.dahuangit.iots.perception.tcpserver.iofilter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 消息转码工厂
 * 
 * @author 黄仁良
 * 
 */
public class PerceptionServerCodesFactory implements ProtocolCodecFactory {
	
	/** 编码器*/
	private final PerceptionServerEncoder encoder;
	
	/** 解码器*/
	private final PerceptionServerDecoder decoder;

	public PerceptionServerCodesFactory() {
		decoder = new PerceptionServerDecoder();
		encoder = new PerceptionServerEncoder();
	}

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
