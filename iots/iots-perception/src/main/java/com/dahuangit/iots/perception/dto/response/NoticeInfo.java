package com.dahuangit.iots.perception.dto.response;

public class NoticeInfo {
	private Long whenL = null;

	private String when = null;

	private String perceptionAddr = null;

	private Integer perceptionId = null;

	private Integer paramId = null;

	private Integer paramValue = null;

	private String paramDesc = null;

	private String paramValueDesc = null;

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public Integer getParamValue() {
		return paramValue;
	}

	public void setParamValue(Integer paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getParamValueDesc() {
		return paramValueDesc;
	}

	public void setParamValueDesc(String paramValueDesc) {
		this.paramValueDesc = paramValueDesc;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Long getWhenL() {
		return whenL;
	}

	public void setWhenL(Long whenL) {
		this.whenL = whenL;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

}
