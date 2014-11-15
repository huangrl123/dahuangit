package com.dahuangit.util;

import java.util.List;

/**
 * 排序相关的工具类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月12日 上午7:34:17
 */
public class SortUtils {

	public static Double getMin(List<Double> list) {
		if (null == list || list.size() == 0) {
			return (double) 0;
		}

		Double[] arr = list.toArray(new Double[list.size()]);

		// 使用冒泡排序法
		Double temp = (double) 0;
		// 趟数
		for (int i = 0; i < arr.length; i++) {

			// 比较次数
			for (int j = 0; j < arr.length - i - 1; j++) {

				// 正序排序
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		return arr[0];
	}

	public static Double getMax(List<Double> list) {
		if (null == list || list.size() == 0) {
			return (double) 0;
		}

		Double[] arr = list.toArray(new Double[list.size()]);

		// 使用冒泡排序法
		Double temp = (double) 0;
		// 趟数
		for (int i = 0; i < arr.length; i++) {

			// 比较次数
			for (int j = 0; j < arr.length - i - 1; j++) {

				// 正序排序
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		return arr[arr.length - 1];
	}

	public static Double getAverage(List<Double> list) {
		if (null == list || list.size() == 0) {
			return (double) 0;
		}

		Double sum = (double) 0;
		for (Double d : list) {
			if(null == d) {
				continue;
			}
			
			sum = sum + d;
		}

		Double average = (double) sum / (double) list.size();
		return average;
	}
}
