package com.dahuangit.water.proxy.dto.response;

public class YongshuiRecord {
	/** 月份 */
	private String month = null;

	/** 消费数据 */
	private String consumedata = null;

	/** 是用电数据 */
	private String consumeWire = null;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getConsumedata() {
		return consumedata;
	}

	public void setConsumedata(String consumedata) {
		this.consumedata = consumedata;
	}

	public String getConsumeWire() {
		return consumeWire;
	}

	public void setConsumeWire(String consumeWire) {
		this.consumeWire = consumeWire;
	}

}
