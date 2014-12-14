package com.dahuangit.base.dto;

import java.util.Date;

public class Request {
	/** 经度 */
	private Double latitude = null;

	/** 维度 */
	private Double longitude = null;

	/** 创建时间 */
	private Date createDateTime = new Date();

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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
