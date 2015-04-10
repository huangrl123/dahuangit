package com.dahuangit.water.proxy.dto.response;

/**
 * 楼栋信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:01:27
 */
public class LdInfo {
	/** 楼栋id 当该节点值为0时，楼栋名称为所有楼栋 */
	private String ldId = null;

	/** 区域名称 */
	private String areaName = null;

	/** 楼栋名称 */
	private String ldName = null;

	public String getLdId() {
		return ldId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setLdId(String ldId) {
		this.ldId = ldId;
	}

	public String getLdName() {
		return ldName;
	}

	public void setLdName(String ldName) {
		this.ldName = ldName;
	}

}
