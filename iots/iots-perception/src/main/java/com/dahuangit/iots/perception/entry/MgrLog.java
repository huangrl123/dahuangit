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
 * 管理日志
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:44:01
 */
@Entity
@Table(name = "t_mgr_log")
public class MgrLog extends BaseModel {
	/** 主键id */
	@Id
	@GeneratedValue
	@Column(name = "mgr_log_id")
	private Integer mgrLogId = null;

	/** '所属用户 */
	@Column(name = "user_id")
	private Integer userId = null;

	/** 管理日志类型 */
	@Column(name = "mgr_log_type_id")
	private Integer mgrLogTypeId = null;

	/** 经度 */
	@Column(name = "latitude")
	private Double latitude = null;

	/** 维度 */
	@Column(name = "longitude")
	private Double longitude = null;

	/** 海拔 */
	@Column(name = "altitude")
	private Double altitude = null;

	/** 方位 */
	@Column(name = "bearing")
	private Double bearing = null;

	/** 创建时间 */
	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	/** 所属用户 */
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user = null;

	/** 所属管理日志类型 */
	@ManyToOne
	@JoinColumn(name = "mgr_log_type_id", insertable = false, updatable = false)
	private MgrLogType mgrLogType = null;

	public Integer getMgrLogId() {
		return mgrLogId;
	}

	public void setMgrLogId(Integer mgrLogId) {
		this.mgrLogId = mgrLogId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMgrLogTypeId() {
		return mgrLogTypeId;
	}

	public void setMgrLogTypeId(Integer mgrLogTypeId) {
		this.mgrLogTypeId = mgrLogTypeId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getBearing() {
		return bearing;
	}

	public void setBearing(Double bearing) {
		this.bearing = bearing;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MgrLogType getMgrLogType() {
		return mgrLogType;
	}

	public void setMgrLogType(MgrLogType mgrLogType) {
		this.mgrLogType = mgrLogType;
	}

}
