package com.dahuangit.iots.perception.dto.response;

/**
 * 某个参数的日志
 * 
 * @author 大黄
 * 
 *         2015年1月23日下午2:24:22
 */
public class LogOfOneParam {
	/** 日志id */
	private Integer logId = null;

	/** 参数值 */
	private Integer value = null;

	/** 参数值描述 */
	private String valueDesc = null;

	/** 创建时间 */
	private String createDate = null;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
