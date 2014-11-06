package com.dahuangit.seobi.proxy.tcpserver.iofilter;

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
public class ProxyServerCodesFactory implements ProtocolCodecFactory {
	
	/** 编码器*/
	private final ProxyServerEncoder encoder;
	
	/** 解码器*/
	private final ProxyServerDecoder decoder;

	public ProxyServerCodesFactory() {
		decoder = new ProxyServerDecoder();
		encoder = new ProxyServerEncoder();
	}

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
