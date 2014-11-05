package com.dahuangit.util.encrypt;

import java.io.UnsupportedEncodingException;

import org.apache.commons.net.util.Base64;

/**
 * base64加密/解密工具类
 * 
 * @author 黄仁良
 * 
 *         创建时间2012-8-2上午9:43:38
 */
public class Base64Utils {
	/**
	 * 解密
	 * 
	 * @param byteStr
	 *            经过base64加密之后的utf-8十六进制字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String byteStr) throws UnsupportedEncodingException {
		byte[] arr = Base64.decodeBase64(byteStr);
		String s = new String(arr, "utf-8");
		return s;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] arr = Base64.decodeBase64("NTU1NXR0MC4w");
		String s = new String(arr, "utf-8");
		System.out.println(s);
	}
}
