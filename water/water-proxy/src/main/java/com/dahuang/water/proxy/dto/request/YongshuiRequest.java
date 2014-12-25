package com.dahuang.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 用水登记
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:44:41
 */
public class YongshuiRequest extends Request {
	/** 项目id */
	private String PrjID = null;

	/** 楼栋id */
	private String LDID = null;

	/** 用水总额 */
	private String TotleWaterMoney = null;

	public String getPrjID() {
		return PrjID;
	}

	public void setPrjID(String prjID) {
		PrjID = prjID;
	}

	public String getLDID() {
		return LDID;
	}

	public void setLDID(String lDID) {
		LDID = lDID;
	}

	public String getTotleWaterMoney() {
		return TotleWaterMoney;
	}

	public void setTotleWaterMoney(String totleWaterMoney) {
		TotleWaterMoney = totleWaterMoney;
	}

}
