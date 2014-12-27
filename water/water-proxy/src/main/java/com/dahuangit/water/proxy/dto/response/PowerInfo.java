package com.dahuangit.water.proxy.dto.response;

/**
 * 权限信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午3:51:57
 */
public class PowerInfo {
	/** 权限id */
	private String powerId = null;

	/** 权限名称 */
	private String powerName = null;

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

}
