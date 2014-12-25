package com.dahuang.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 预警请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:17:19
 */
public class YujingRequest extends Request {

	/** 项目id */
	private String PrjID = null;

	public String getPrjID() {
		return PrjID;
	}

	public void setPrjID(String prjID) {
		PrjID = prjID;
	}

}
