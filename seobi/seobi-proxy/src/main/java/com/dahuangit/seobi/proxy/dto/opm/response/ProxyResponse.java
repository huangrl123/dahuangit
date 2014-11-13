package com.dahuangit.seobi.proxy.dto.opm.response;


public class ProxyResponse {
	private Integer pid = null;

	private String proxyIp = null;

	private Integer proxyPort = null;

	/** telnet是否通 */
	private String isTelnetAvailable = null;

	/** http get方法是否可用 */
	private String isHttpGetAvailable = null;

	/** http post方法是否可用 */
	private String isHttpPostAvailable = null;

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

	public String getIsTelnetAvailable() {
		return isTelnetAvailable;
	}

	public void setIsTelnetAvailable(String isTelnetAvailable) {
		this.isTelnetAvailable = isTelnetAvailable;
	}

	public String getIsHttpGetAvailable() {
		return isHttpGetAvailable;
	}

	public void setIsHttpGetAvailable(String isHttpGetAvailable) {
		this.isHttpGetAvailable = isHttpGetAvailable;
	}

	public String getIsHttpPostAvailable() {
		return isHttpPostAvailable;
	}

	public void setIsHttpPostAvailable(String isHttpPostAvailable) {
		this.isHttpPostAvailable = isHttpPostAvailable;
	}

}
