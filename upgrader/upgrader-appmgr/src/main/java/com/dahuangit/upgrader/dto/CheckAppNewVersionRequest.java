package com.dahuangit.upgrader.dto;

/**
 * 检查是否有新的app版本
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午11:57:06
 */
public class CheckAppNewVersionRequest {

	private String currentAppVersion = null;

	public String getCurrentAppVersion() {
		return currentAppVersion;
	}

	public void setCurrentAppVersion(String currentAppVersion) {
		this.currentAppVersion = currentAppVersion;
	}

}
