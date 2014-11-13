package com.dahuangit.util;

import java.text.NumberFormat;

import org.apache.commons.lang.Validate;

/**
 * 数字相关的工具类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月12日 上午11:47:43
 */
public class NumberUtils {

	/**
	 * 将数字转换为百分比字符
	 * 
	 * @param d
	 *            传入的数值
	 * @return
	 */
	public static String number2percent(Double d) {
		Validate.notNull(d, "传入的数值不能为null");

		NumberFormat nt = NumberFormat.getPercentInstance();

		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);

		return nt.format(d);
	}
}
