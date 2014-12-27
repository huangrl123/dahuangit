package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 监控request
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:07:43
 */
public class JiankongRequest extends Request {
	/** 开始时间 */
	private String beginDT = null;

	/** 结束时间 */
	private String EndDT = null;

	/** 项目id */
	private String PrjID = null;

	public String getBeginDT() {
		return beginDT;
	}

	public void setBeginDT(String beginDT) {
		this.beginDT = beginDT;
	}

	public String getEndDT() {
		return EndDT;
	}

	public void setEndDT(String endDT) {
		EndDT = endDT;
	}

	public String getPrjID() {
		return PrjID;
	}

	public void setPrjID(String prjID) {
		PrjID = prjID;
	}

}
