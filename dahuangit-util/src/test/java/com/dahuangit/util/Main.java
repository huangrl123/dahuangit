package com.dahuangit.util;

import com.dahuangit.util.coder.ByteUtils;

public class Main {

	public static void main(String[] args) {
//		long l = 436456546457l;
//		byte[] btArr = ByteUtils.long2ByteArray(l);
//		System.out.println(ByteUtils.byteArrToLong(btArr));
		
		int l = 7869765;
		byte[] btArr = ByteUtils.intToByteArray(l);
		System.out.println(ByteUtils.byteArrToInt(btArr));
		
	}

}
