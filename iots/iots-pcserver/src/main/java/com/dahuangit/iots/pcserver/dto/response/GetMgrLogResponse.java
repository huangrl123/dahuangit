package com.dahuangit.iots.pcserver.dto.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.dahuangit.base.dto.Response;
import com.dahuangit.util.bean.dto.DtoField;
import com.dahuangit.util.spring.JsonDateSerializer;

public class GetMgrLogResponse extends Response {
	/** 主键id */
	private Integer mgrLogId = null;

	/** '所属用户 */
	private Integer userId = null;

	/** 管理日志类型 */
	private Integer mgrLogTypeId = null;

	/** 经度 */
	private Double latitude = null;

	/** 维度 */
	private Double longitude = null;

	/** 海拔 */
	private Double altitude = null;

	/** 方位 */
	private Double bearing = null;

	/** 创建时间 */
	private Date createDateTime;

	/** 所属用户 */
	@DtoField(field = "user.userAbbr")
	private String userName = null;

	/** 所属管理日志类型 */
	@DtoField(field = "mgrLogType.mgrLogTypeName")
	private String mgrLogTypeName = null;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMgrLogTypeName() {
		return mgrLogTypeName;
	}

	public void setMgrLogTypeName(String mgrLogTypeName) {
		this.mgrLogTypeName = mgrLogTypeName;
	}

}
