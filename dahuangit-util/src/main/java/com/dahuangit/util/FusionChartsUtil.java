package com.dahuangit.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

public class FusionChartsUtil {

	private static final Logger logger = Logger.getLogger(FusionChartsUtil.class);
	private static final String[] array = { "C61011", "E90686", "821EBC", "1638E6", "ADEE0", "D719A5", "4572A7",
			"AA4643", "89A54E", "71588F", "4198AF", "DB843D", "4F81BD", "C0504D", "9BBB59", "8064A2", "4BACC6",
			"F79646", "93A9CF", "D19392", "B9CD6", "A99BBD", "91C3D5", "F9B590", "BCC8DF" };

	/**
	 * 得到FusionCharts所需要的数据工具类
	 * 
	 * @param lists
	 * @param value1
	 * @param value2
	 * @param bool
	 * @return
	 */
	public static <X> String getDataXml(List<X> lists, String value1, String value2, String value3) {
		StringBuffer sb = new StringBuffer();
		try {
			int i = 0;
			for (X object : lists) {
				if (i > array.length) {
					i = 0;
				}
				sb.append("<set name='" + BeanUtils.getProperty(object, value1) + "' value='"
						+ BeanUtils.getProperty(object, value2) + "' color='" + array[i++]
						+ "' link='j-OpenChildChart-" + BeanUtils.getProperty(object, value3) + "' />");
			}
		} catch (Exception e) {
			logger.error("转换数据出错", e);
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static <X> String getDataXmlNotLink(List<X> lists, String value1, String value2, Boolean bool) {
		StringBuffer sb = new StringBuffer();
		try {
			int i = 0;
			for (X object : lists) {
				if (i > array.length) {
					i = 0;
				}
				if (bool) {
					if (!BeanUtils.getProperty(object, value2).toString().equals("0")) {
						sb.append("<set name='" + BeanUtils.getProperty(object, value1) + "' value='"
								+ BeanUtils.getProperty(object, value2) + "' color='" + array[i++] + "' />");
					}
				} else {
					sb.append("<set name='" + BeanUtils.getProperty(object, value1) + "' value='"
							+ BeanUtils.getProperty(object, value2) + "' color='" + array[i++] + "'/>");
				}

			}
		} catch (Exception e) {
			logger.error("转换数据出错", e);
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 获得组合图表数据
	 * 
	 * @param <X>
	 * @param map
	 * @param deptName
	 * @param countNum
	 * @return
	 */
	public static <X> String getMsColumnDate(Map<String, List<X>> map, String deptName, String countNum, String[] strs) {
		StringBuffer sb = new StringBuffer("<categories fontSize='14'>");
		StringBuffer[] sets = new StringBuffer[map.keySet().size()];
		try {
			int i = 0;
			int j = 0;
			int q = 0;
			for (String str : strs) {
				sets[q] = new StringBuffer("");
				for (X obj : map.get(str)) {
					i++;
					sets[q].append("<set value = '" + BeanUtils.getProperty(obj, countNum) + "'/>");
					if (q == 0) {
						sb.append(" <category name='" + BeanUtils.getProperty(obj, deptName) + "' />");
						if (i == map.get(str).size()) {
							sb.append("</categories>");
						}
					}
				}
				sb.append("<dataset seriesname='" + str + "' color='" + array[j++] + "'>");
				sb.append(sets[q]);
				sb.append("</dataset>");

				q++;
			}
		} catch (Exception e) {
			logger.error("转换数据出错", e);
			e.printStackTrace();
		}
		System.out.println(sb);
		return sb.toString();
	}
	
	
}
