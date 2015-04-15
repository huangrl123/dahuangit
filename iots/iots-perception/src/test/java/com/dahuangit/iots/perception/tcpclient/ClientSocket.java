package com.dahuangit.iots.perception.tcpclient;

import java.net.Socket;

import com.dahuangit.util.coder.ByteUtils;

public class ClientSocket {
	public static void main(String[] args) {

		try {
			Socket socket = new Socket("120.24.86.107", 9999);

			byte[] content = new byte[72];
			// 帧序列号
			content[0] = (byte) 0xA1;
			content[1] = 0x08;
			long seq = 444l;
			//System.arraycopy(ByteUtils.longToByteArray(seq), 0, content, 2, 8);
			// 帧总长度
			content[10] = (byte) 0xA2;
			content[11] = 0x04;
			//System.arraycopy(ByteUtils.intToByteArray(68), 0, content, 12, 4);
			// 业务类型
			content[16] = (byte) 0xA3;
			content[17] = 0x01;
			content[18] = 0x01;
			// CRC32校验和
			content[19] = (byte) 0xA4;
			content[20] = 0x08;
			// 设备类型
			content[29] = (byte) 0xA5;
			content[30] = 0x01;
			byte bytePerceptionTypeId = 2;
			content[31] = bytePerceptionTypeId;
			// 电机地址
			content[32] = (byte) 0xB1;
			content[33] = 0x20;
			System.arraycopy("huizhoueden".getBytes(), 0, content, 34, "huizhoueden".getBytes().length);
			// 操作标识
			content[66] = (byte) 0xB2;
			content[67] = 0x01;
			content[68] = 0x00;
			content[69] = 0x00;
			content[70] = 0x00;
			content[71] = 0x01;

			long crc32l = ByteUtils.byteArrCRC32Value(content);
			//System.arraycopy(ByteUtils.longToByteArray(crc32l), 0, content, 21, 8);

			socket.getOutputStream().write(content);
			
			Thread.sleep(60 * 60 * 1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
