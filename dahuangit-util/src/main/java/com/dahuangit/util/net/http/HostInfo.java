package com.dahuangit.util.net.http;

public class HostInfo {
	/** 主机地址*/
	private String addr = null;

	/** 主机端口*/
	private Integer port = null;

	/** 主机支持的编码格式*/
	private String encode = null;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

}
