package com.dahuangit.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件唯一号生成器
 * 
 * @author 黄仁良
 * 
 */
public class IDGenerator {
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALL_NUM_CHAR = "0123456789";

	public static synchronized String generate() {
		StringBuffer randomStr = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = format.format(new Date());
		randomStr.append(dateStr);
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			randomStr.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
		}
		return randomStr.toString();
	}

	/**
	 * 产生一个15位长度的数字
	 * 
	 * @return
	 */
	public static synchronized Long generateNum() {
		StringBuffer randomStr = new StringBuffer();
		Random random = new Random();
		
		for (int i = 0; i < 2; i++) {
			randomStr.append(ALL_NUM_CHAR.charAt(random.nextInt(ALL_NUM_CHAR.length())));
		}
		
		String s = String.valueOf(System.currentTimeMillis());// 13位
		s = s + randomStr.toString();
		
		return new Long(s);
	}
	
	public static void main(String[] args){
		
		System.out.println(""+IDGenerator.generateNum());
		System.out.println("140980215112227".length());
	}
	
	
	
}
