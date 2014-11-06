package com.dahuangit.seobi.proxy.dto.opm.response;

import java.util.Date;

import com.dahuangit.util.bean.dto.Dto;

@Dto
public class ProxyResponse {
	private Integer pid = null;

	private String proxyIp = null;

	private Integer proxyPort = null;

	private String protocol = null;

	private String available = null;

	private String lastTestTime = null;

	private String remark = null;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getLastTestTime() {
		return lastTestTime;
	}

	public void setLastTestTime(String lastTestTime) {
		this.lastTestTime = lastTestTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
