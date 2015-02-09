package com.dahuangit.iots.app.dto.request;

import java.io.UnsupportedEncodingException;

import com.dahuangit.base.dto.Request;

/**
 * 获取运行日志id请求类
 * 
 * @author 大黄
 * 
 *         2015年1月29日上午10:44:49
 */
public class getRuntimeLogByParamIdReq extends Request {

	/** 设备id */
	private Integer perceptionId = null;

	/** 设备名称 */
	private String perceptionName = null;

	/** 参数id */
	private Integer paramId = null;

	/** 参数描述 */
	private String paramDesc = null;

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getPerceptionName() {

		try {
			return new String(perceptionName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getParamDesc() {

		try {
			return new String(this.paramDesc.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

}
