package com.dahuangit.iots.perception.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dahuangit.base.entry.BaseModel;

/**
 * 感知端实体类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:23:29
 */
@Entity
@Table(name = "t_perception")
public class Perception extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "p_id")
	private Integer perceptionId = null;

	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	@Column(name = "p_addr")
	private String perceptionAddr = null;

	@Column(name = "p_name")
	private String perceptionName = null;

	@Column(name = "install_site")
	private String installSite = null;

	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	@Column(name = "last_comm_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastCommTime = null;

	@ManyToOne
	@JoinColumn(name = "pt_id", insertable = false, updatable = false)
	private PerceptionType perceptionType = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(Date lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public PerceptionType getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(PerceptionType perceptionType) {
		this.perceptionType = perceptionType;
	}

}
