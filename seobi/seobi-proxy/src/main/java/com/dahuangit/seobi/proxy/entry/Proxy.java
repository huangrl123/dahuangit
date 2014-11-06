package com.dahuangit.seobi.proxy.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.dahuangit.base.entry.BaseModel;

@Entity
@Table(name = "t_proxy")
public class Proxy extends BaseModel {
	/** 主键ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pid")
	private Integer pid = null;

	/** 代理服务器ip */
	@Column(name = "proxy_ip")
	private String proxyIp = null;

	/** 代理服务器端口 */
	@Column(name = "proxy_port")
	private Integer proxyPort = null;

	/** 代理服务器通信协议 */
	@Column(name = "protocol")
	private String protocol = null;

	/** 是否可用 */
	@Column(name = "proxy_available")
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean available = false;

	/** 最后测试时间,默认值为当前时间 */
	@Column(name = "last_test_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTestTime = new Date();

	/** 备注 */
	@Column(name = "remark")
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

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Date getLastTestTime() {
		return lastTestTime;
	}

	public void setLastTestTime(Date lastTestTime) {
		this.lastTestTime = lastTestTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
