package com.dahuang.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 损益请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:26:07
 */
public class SunyiRequest extends Request {
	/** 项目id */
	private String PrjID = null;

	/**
	 * 请求动作类型 1：学期概况 2：学期月份收益 3：近三年学期收益
	 * 
	 * 多个请求动作类型以逗号分隔
	 */
	private String actionType = null;

	public String getPrjID() {
		return PrjID;
	}

	public void setPrjID(String prjID) {
		PrjID = prjID;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
