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

	public static int getMin(List<Integer> list) {
		Integer[] arr = list.toArray(new Integer[list.size()]);
		
		// 使用冒泡排序法
		int temp;
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

}
