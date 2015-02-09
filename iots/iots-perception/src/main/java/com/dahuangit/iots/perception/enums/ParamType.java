package com.dahuangit.iots.perception.enums;

/**
 * 参数类型
 * 
 * @author 黄仁良
 * 
 *         创建于 2010-10-20 上午12:28:26
 */
public enum ParamType implements DescribableEnum {

	/**
	 * 文本
	 */
	TEXT("文本"),

	/**
	 * 下拉选
	 */
	COMBOBOX("下拉选");

	private String desc;

	private ParamType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
