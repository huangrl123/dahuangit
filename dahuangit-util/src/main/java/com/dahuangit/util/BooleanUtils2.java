package com.dahuangit.util;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.Validate;

/**
 * BooleanUtils扩展
 * 
 * @author 黄仁良
 * 
 */
public class BooleanUtils2 extends BooleanUtils {
	/**
	 * 将Y或N转换为true或false
	 * 
	 * @param yOrN
	 * @return
	 */
	public static Boolean yNToBoolean(String yOrN) {
		Validate.notNull(yOrN, "传入参数不能null");
		if ("Y".equalsIgnoreCase(yOrN)) {
			return Boolean.TRUE;
		} else if ("N".equalsIgnoreCase(yOrN)) {
			return Boolean.FALSE;
		} else {
			throw new IllegalArgumentException("传入的值[" + yOrN + "]不在[Y,y,N,n]范围");
		}
	}

	/**
	 * 将true或false字符串转换为true或false
	 * 
	 * @param yOrN
	 * @return
	 */
	
	public static Boolean trueFalseToBoolean(String trueOrFalse) {
		Validate.notNull(trueOrFalse, "传入参数不能null");
		if ("true".equalsIgnoreCase(trueOrFalse)) {
			return Boolean.TRUE;
		} else if ("false".equalsIgnoreCase(trueOrFalse)) {
			return Boolean.FALSE;
		} else {
			throw new IllegalArgumentException("传入的值[" + trueOrFalse + "]不在[TRUE,true,FALSE,false]范围");
		}
	}

	/**
	 * 将bool转换为Y N形式，例如true:Y;false:N
	 * 
	 * @param bool
	 * @return
	 */
	public static String toStringYN(Boolean bool) {
		Validate.notNull(bool, "传入参数不能null");
		
		return toString(bool, "Y", "N", null);
	}
	
}
