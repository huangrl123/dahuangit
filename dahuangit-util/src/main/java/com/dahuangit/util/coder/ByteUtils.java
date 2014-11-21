package com.dahuangit.util.coder;

import java.util.zip.CRC32;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;

/**
 * byte数组相关的工具类
 * 
 * @author 黄仁良
 * 
 */
public class ByteUtils {

	private static final Logger log = Log4jUtils.getLogger(ByteUtils.class);

	/**
	 * 将byte数组转换为hex字符串形式
	 * 
	 * @param btArr
	 * @return
	 */
	public static String byteArrToHexString(byte[] btArr) {
		Validate.notNull(btArr, "byte数组不能为null");

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < btArr.length; i++) {
			String hex = Integer.toHexString(btArr[i] & 0xFF);

			if (hex.length() == 1) {
				hex = '0' + hex;
				sb.append(hex);
				sb.append(" ");
				continue;
			}

			sb.append(hex);
			sb.append(" ");
		}

		return sb.toString().toUpperCase();
	}

	public static String byteToHexString(byte b) {
		StringBuffer sb = new StringBuffer();

		String hex = Integer.toHexString(b & 0xFF);

		if (hex.length() == 1) {
			hex = '0' + hex;
			sb.append(hex);
		}

		sb.append(" ");

		return sb.toString().toUpperCase();
	}

	/**
	 * 计算byte数组的crc32校验和
	 * 
	 * @param btArr
	 * @return
	 */
	public static long byteArrCRC32Value(byte[] btArr) {
		Validate.notNull(btArr, "byte数组不能为null");

		CRC32 crc32 = new CRC32();
		crc32.update(btArr);

		return crc32.getValue();
	}

	/**
	 * 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
	 * 
	 * @param bRefArr
	 * @return
	 */
	public static int byteArrToInt(byte[] btArr) {
		Validate.notNull(btArr, "byte数组不能为null");

		if (btArr.length > 4) {
			throw new RuntimeException("不能将长度大于4的byte数组转换成int");
		}

		log.debug("将byte数组bRefArr转为一个整数,btArr=" + ByteUtils.byteArrToHexString(btArr));

		byte[] content = new byte[4];
		// copy到低位
		System.arraycopy(btArr, 0, content, 4 - btArr.length, btArr.length);

		int value = 0;

		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (content[i] & 0x000000FF) << shift;
		}

		return value;
	}

	/**
	 * 将int转换为byte数组
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		if (i == 0) {
			return new byte[] { 0x00000000 };
		}
		
		byte[] result = new byte[4];
		
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		
		return result;
	}
	
	public static byte oneIntToByteArray(int i) {
		byte[] arr = ByteUtils.intToByteArray(i);
		byte bopt = arr[arr.length - 1];
		return bopt;
	}

	/**
	 * 将byte数组转换为long
	 * 
	 * @param btArr
	 * @return
	 */
	public static long byteArrToLong(byte[] btArr) {

		return ((((long) btArr[0] & 0xff) << 56) | (((long) btArr[1] & 0xff) << 48) | (((long) btArr[2] & 0xff) << 40)
				| (((long) btArr[3] & 0xff) << 32) | (((long) btArr[4] & 0xff) << 24)
				| (((long) btArr[5] & 0xff) << 16) | (((long) btArr[6] & 0xff) << 8) | (((long) btArr[7] & 0xff) << 0));

	}

	/**
	 * 将long类型换行为byte数组
	 * 
	 * @param l
	 * @return
	 */
	public static byte[] longToByteArray(long l) {
		log.debug("将long类型换行为byte数组,longValue(string)=" + String.valueOf(l));

		byte[] result = new byte[8];

		result[0] = (byte) (l >> 56);
		result[1] = (byte) (l >> 48);
		result[2] = (byte) (l >> 40);
		result[3] = (byte) (l >> 32);
		result[4] = (byte) (l >> 24);
		result[5] = (byte) (l >> 16);
		result[6] = (byte) (l >> 8);
		result[7] = (byte) (l >> 0);

		log.debug("将long类型换行为byte数组,longValue(byte)=" + ByteUtils.byteArrToHexString(result));

		return result;
	}
}
