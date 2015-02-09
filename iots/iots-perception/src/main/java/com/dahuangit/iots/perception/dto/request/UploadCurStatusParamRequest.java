package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 感知端设备上报当前状态
 * 
 * @author 大黄
 * 
 *         2015年1月20日上午10:28:57
 */
public class UploadCurStatusParamRequest extends Request {

	/** 设备地址 */
	private String perceptionAddr = null;

	/** XML字符串,见下表 */
	private String perceptionStatusInfoXml = null;

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionStatusInfoXml() {
		return perceptionStatusInfoXml;
	}

	public void setPerceptionStatusInfoXml(String perceptionStatusInfoXml) {
		this.perceptionStatusInfoXml = perceptionStatusInfoXml;
	}

}
