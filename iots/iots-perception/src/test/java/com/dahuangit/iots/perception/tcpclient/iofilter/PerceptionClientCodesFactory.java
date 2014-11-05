package com.dahuangit.iots.perception.tcpclient.iofilter;

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
public class PerceptionClientCodesFactory implements ProtocolCodecFactory {
	
	/** 编码器*/
	private final PerceptionClientEncoder encoder;
	
	/** 解码器*/
	private final PerceptionClientDecoder decoder;

	public PerceptionClientCodesFactory() {
		decoder = new PerceptionClientDecoder();
		encoder = new PerceptionClientEncoder();
	}

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
