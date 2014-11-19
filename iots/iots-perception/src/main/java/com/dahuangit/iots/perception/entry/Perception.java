package com.dahuangit.iots.perception.entry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseAuditableModel;

/**
 * 感知端实体类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:23:29
 */
@Entity
@Table(name = "t_perception")
public class Perception extends BaseAuditableModel {
	/** 感知端主键 */
	@Id
	@GeneratedValue
	@Column(name = "pid")
	private Integer pid = null;

	/** 感知端设备类型 1:2+2 设备 2:6+6设备 */
	@Column(name = "ptype")
	private String perceptionType = null;

	/** 感知端设备地址(mac地址) */
	@Column(name = "paddr")
	private String perceptionAddr = null;

	/** 感知端设备名称 */
	@Column(name = "pname")
	private String perceptionName = null;

	/** 安装地点 */
	@Column(name = "install_site")
	private String installSite = null;

	/** 本感知端的参数集合 双向一对多关联 */
	@OneToMany(mappedBy = "perception", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<PerceptionParam> perceptionParams = null;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(String perceptionType) {
		this.perceptionType = perceptionType;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getInstallSite() {
		return installSite;
	}

	public void setInstallSite(String installSite) {
		this.installSite = installSite;
	}

	public List<PerceptionParam> getPerceptionParams() {
		return perceptionParams;
	}

	public void setPerceptionParams(List<PerceptionParam> perceptionParams) {
		this.perceptionParams = perceptionParams;
	}

}
