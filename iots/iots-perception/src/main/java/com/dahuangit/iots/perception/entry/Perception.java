package com.dahuangit.iots.perception.entry;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	/** 主键id */
	@Id
	@GeneratedValue
	@Column(name = "p_id")
	private Integer perceptionId = null;

	/** 所属设备类型 */
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	/** 设备地址 */
	@Column(name = "p_addr")
	private String perceptionAddr = null;

	/** 设备名称 */
	@Column(name = "p_name")
	private String perceptionName = null;

	/** 安装位置 */
	@Column(name = "install_site")
	private String installSite = null;

	/** 创建时间 */
	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	/** 在线状态 */
	@Column(name = "online_status")
	private Integer onlineStatus = 0;

	/** 最后通信时间 */
	@Column(name = "last_comm_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastCommTime = null;

	/** 所属的设备类型 */
	@ManyToOne
	@JoinColumn(name = "pt_id", insertable = false, updatable = false)
	private PerceptionType perceptionType = null;

	/** 该设备受哪些用户的管理 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "t_user_perception", joinColumns = { @JoinColumn(name = "perception_id", referencedColumnName = "p_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	private List<User> managers = null;

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

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}

}
