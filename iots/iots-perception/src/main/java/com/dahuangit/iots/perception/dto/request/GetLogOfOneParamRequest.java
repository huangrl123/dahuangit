package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 查询某个具体参数的日志请求类
 * 
 * @author 大黄
 * 
 *         2015年1月23日下午2:15:29
 */
public class GetLogOfOneParamRequest extends Request {

	/** 设备id */
	private Integer perceptionId = null;

	/** 参数id */
	private Integer paramId = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

}
