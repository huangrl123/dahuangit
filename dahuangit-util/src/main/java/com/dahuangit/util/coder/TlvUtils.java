package com.dahuangit.util.coder;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;

public class TlvUtils {

	private static final Logger log = Log4jUtils.getLogger(TlvUtils.class);

	/**
	 * 构建tlv
	 * 
	 * @param tag
	 * @param length
	 * @param value
	 * @return
	 */
	public static byte[] buildTlv(byte[] tag, byte[] length, byte[] value) {
		byte[] content = new byte[tag.length + length.length + value.length];

		int index = 0;

		for (byte b : tag) {
			content[index++] = b;
		}

		for (byte b : length) {
			content[index++] = b;
		}

		for (byte b : value) {
			content[index++] = b;
		}

		return content;
	}

	/**
	 * 获取int类型的tlv值
	 * 
	 * @param content
	 * @param tag
	 * @param length
	 * @return
	 */
	public static int getTlvIntValue(byte[] content, byte tag) {
		int value = 0;

		byte[] b = new byte[4];

		for (int i = 0; i < content.length; i++) {
			if (content[i] == tag) {
				System.arraycopy(content, i + 1 + 1, b, 0, 4);
				break;
			}
		}

		value = ByteUtils.byteArrToInt(b);

		return value;
	}

	/**
	 * 获取long类型的tlv值
	 * 
	 * @param content
	 * @param tag
	 * @param length
	 * @return
	 */
	public static long getTlvLongValue(byte[] content, byte tag) {
		log.debug("获取long类型的tlv值,content=" + ByteUtils.byteArrToHexString(content));
		log.debug("获取long类型的tlv值,tag=" + ByteUtils.byteArrToHexString(new byte[] { tag }));

		long value = 0;

		byte[] b = new byte[8];

		for (int i = 0; i < content.length; i++) {
			if (content[i] == tag) {

				System.arraycopy(content, i + 1 + 1, b, 0, 8);

				log.debug("获取long类型的tlv值,length=" + b.length);
				log.debug("获取long类型的tlv值,value(byte)=" + ByteUtils.byteArrToHexString(b));

				break;
			}
		}

		value = ByteUtils.byteArrToLong(b);
		log.debug("获取long类型的tlv值,value(string)=" + String.valueOf(value));

		return value;
	}

	/**
	 * 获取String类型的tlv值
	 * 
	 * @param content
	 * @param tag
	 * @param length
	 * @return
	 */
	public static String getTlvStringValue(byte[] content, byte tag, int length) {
		String value = null;

		byte[] b = new byte[length];

		for (int i = 0; i < content.length; i++) {
			if (content[i] == tag) {

				// 取出字符串本身的length所占的字节数
				System.arraycopy(content, i + getIntValueRealByteCount(length), b, 0, length);
				break;
			}
		}

		value = new String(b);

		return value;
	}

	private static int getIntValueRealByteCount(int value) {
		int byteCount = 0;

		if (0 <= value && value <= 255) {
			byteCount = 1;
		} else if (256 <= value && value <= 65535) {
			byteCount = 2;
		} else if (65536 <= value && value <= 16777215) {
			byteCount = 3;
		} else if (16777216 <= value && value <= 16777214) {
			byteCount = 4;
		} else {
			throw new RuntimeException("不支持的长度数据类型");
		}

		return byteCount;
	}

	/**
	 * 获取byte的tlv值
	 * 
	 * @param content
	 * @param tag
	 * @return
	 */
	public static byte getByteValue(byte[] content, byte tag) {
		for (int i = 0; i < content.length; i++) {
			if (content[i] == tag) {

				return content[i + 1 + 1];
			}
		}

		return 0x00;
	}
}
