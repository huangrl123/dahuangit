package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.ComboboxData;

/**
 * 参数
 * 
 * @author 大黄
 * 
 *         2015年1月20日上午10:18:40
 */
public class ParamInfo {

	/** 参数id */
	private Integer paramId = null;

	/** 参数描述 */
	private String paramDesc = null;

	/** 参数值 */
	private Integer paramValue = null;

	/** 参数值描述 */
	private String paramValueDesc = null;

	/** 参数类型 */
	private String paramType = null;

	/** 下拉选数据(当参数为可编辑参数时有效) */
	private ComboboxData comboboxData = null;

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

	public String getParamValueDesc() {
		return paramValueDesc;
	}

	public void setParamValueDesc(String paramValueDesc) {
		this.paramValueDesc = paramValueDesc;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public ComboboxData getComboboxData() {
		return comboboxData;
	}

	public void setComboboxData(ComboboxData comboboxData) {
		this.comboboxData = comboboxData;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

}
