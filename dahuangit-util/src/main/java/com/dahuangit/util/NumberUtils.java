package com.dahuangit.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 将以分号分隔的ID字符串转化为List<Long>形式
	 * 
	 * @param IDs
	 * @return
	 */
	public static List<Long> ids2longList(String IDs) {
		List<Long> list = new ArrayList<Long>();
		String[] arr = IDs.split(";");
		for (String s : arr) {
			Long id;
			try {
				id = new Long(s);
				list.add(id);
			} catch (NumberFormatException e) {
			}
		}
		return list;
	}

	/**
	 * 将以逗号分隔的ID字符串转化为List<Integer>形式
	 * 
	 * @param IDs
	 * @return
	 */
	public static List<Integer> ids2integerList(String IDs) {
		List<Integer> list = new ArrayList<Integer>();
		String[] arr = IDs.split(";");
		for (String s : arr) {
			Integer id;
			try {
				id = new Integer(s);
				list.add(id);
			} catch (NumberFormatException e) {
			}
		}
		return list;
	}

	/**
	 * 将integer类型的idlist转换为以逗号分隔的字符串
	 * 
	 * @param iList
	 * @return
	 */
	public static String integerList2idsStr(List<Integer> iList) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < iList.size(); i++) {
			Integer in = iList.get(i);
			if (null == in) {
				continue;
			}

			sb.append(in);

			if (i != iList.size() - 1) {
				sb.append(";");
			}
		}

		return sb.toString();
	}

	/**
	 * 将Long类型的idlist转换为以逗号分隔的字符串
	 * 
	 * @param iList
	 * @return
	 */
	public static String longList2idsStr(List<Long> iList) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < iList.size(); i++) {
			Long in = iList.get(i);
			if (null == in) {
				continue;
			}

			sb.append(in);

			if (i != iList.size() - 1) {
				sb.append(";");
			}
		}

		return sb.toString();
	}
}
