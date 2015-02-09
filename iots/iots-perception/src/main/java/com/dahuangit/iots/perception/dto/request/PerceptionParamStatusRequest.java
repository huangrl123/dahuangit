package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 设备状态查询请求类
 * 
 * @author 大黄
 * 
 *         2015年1月22日下午10:24:07
 */
public class PerceptionParamStatusRequest extends Request {

	/** 设备id */
	private Integer perceptionId = null;

	/** 是否为初始化 */
	private boolean isInit = true;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

}
