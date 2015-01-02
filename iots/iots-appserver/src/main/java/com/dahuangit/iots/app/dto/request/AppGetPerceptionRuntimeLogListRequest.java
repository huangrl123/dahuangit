package com.dahuangit.iots.app.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 感知端运行日志列表请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午10:35:29
 */
public class AppGetPerceptionRuntimeLogListRequest extends Request {
	/** 感知端id */
	private Integer perceptionId = null;
   
	
	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

}
