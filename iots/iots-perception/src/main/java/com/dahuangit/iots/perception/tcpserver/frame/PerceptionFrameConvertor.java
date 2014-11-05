package com.dahuangit.iots.perception.tcpserver.frame;

import org.apache.log4j.Logger;

import com.dahuangit.util.coder.ByteUtils;
import com.dahuangit.util.coder.TlvUtils;
import com.dahuangit.util.log.Log4jUtils;

public class PerceptionFrameConvertor {

	private static final Logger log = Log4jUtils.getLogger(PerceptionFrameConvertor.class);

	/**
	 * 帧转换为电机状态信息
	 * 
	 * @param content
	 * @return
	 */
	public static PerceptionFrame ByteArrToPerceptionFrame(byte[] content) {
		log.debug("将帧转换为对象...");
		log.debug("帧数组content=" + ByteUtils.byteArrToHexString(content));

		PerceptionFrame frame = new PerceptionFrame();

		// /////////////////设置帧头///////////////////
		// 帧序列号
		long seq = TlvUtils.getTlvLongValue(content, (byte) 0xA1);
		log.debug("从帧里解析出来的帧序列号seq=" + seq);
		frame.setSeq(seq);

		// 帧总长度
		int length = TlvUtils.getTlvIntValue(content, (byte) 0xA2);
		log.debug("从帧里解析出来的帧总长度length=" + length);
		frame.setLength(length);

		// 业务类型
		byte busType = TlvUtils.getByteValue(content, (byte) 0xA3);
		log.debug("从帧里解析出来的业务类型busType=" + ByteUtils.byteToHexString(busType));
		frame.setBusType(busType);

		// CRC32校验和
		long crc32 = TlvUtils.getTlvLongValue(content, (byte) 0xA4);
		log.debug("从帧里解析出来的CRC32校验和crc32=" + crc32);

		// /////////////////设置帧体///////////////////
		byte opt = (byte) 0x00;

		// 电机地址
		String machineAddr = TlvUtils.getTlvStringValue(content, (byte) 0xB1, 32);
		frame.setMachineAddr(machineAddr);

		byte rotateStatus = (byte) 0x00;
		byte switchStatus = (byte) 0x00;
		byte result = (byte) 0x01;

		// 取出操作符号
		for (int i = 0; i < content.length; i++) {
			if (content[i] == (byte) 0xB2) {
				opt = content[i + 1 + 1];
				break;
			}
		}

		if (opt == (byte) 0x00) {
			throw new RuntimeException("在帧内容里找不到操作标识");
		}

		// 操作标识
		frame.setOperateFlag(opt);

		// 设置帧体,根据操作符号设置值
		switch (opt) {
		case 0x01:// 电机向服务器发送状态
			if (busType == (byte) 0x01) {// 请求帧
				// 旋转状态
				rotateStatus = TlvUtils.getByteValue(content, (byte) 0xB3);
				frame.setRotateStatus(rotateStatus);

				// 开关状态
				switchStatus = TlvUtils.getByteValue(content, (byte) 0xB4);
				frame.setSwitchStatus(switchStatus);
			} else if (busType == (byte) 0x02) {// 响应帧
				// 处理结果
				result = TlvUtils.getByteValue(content, (byte) 0xB5);
				frame.setResult(result);
			}

			break;

		case 0x02:// 服务器向电机查询状态
			if (busType == (byte) 0x02) {// 响应帧
				// 旋转状态
				rotateStatus = TlvUtils.getByteValue(content, (byte) 0xB3);
				frame.setRotateStatus(rotateStatus);

				// 开关状态
				switchStatus = TlvUtils.getByteValue(content, (byte) 0xB4);
				frame.setSwitchStatus(switchStatus);

				// 处理结果
				result = TlvUtils.getByteValue(content, (byte) 0xB5);
				frame.setResult(result);
			}

			break;

		case 0x03:// 服务器远程控制电机工作状态：远程开
		case 0x04:// 服务器远程控制电机工作状态：远程关
		case 0x05:// 服务器远程控制电机工作状态：远程正转
		case 0x06:// 服务器远程控制电机工作状态：远程反转
			if (busType == (byte) 0x02) {// 响应帧
				// 处理结果
				result = TlvUtils.getByteValue(content, (byte) 0xB5);
				frame.setResult(result);
			}

			break;

		default:
			throw new RuntimeException("找不到相应的操作类型:" + opt);
		}

		return frame;
	}

	/**
	 * 电机状态信息从bean转换为帧
	 * 
	 * @param commBean22
	 * @return
	 */
	public static byte[] PerceptionFrameToByteArray(PerceptionFrame frame) {
		log.debug("将对象转换为帧数组...");

		byte[] content = null;

		// /////////////////设置帧体///////////////////
		log.debug("开始debug帧体信息...");

		long seq = frame.getSeq();
		log.debug("frame.seq=" + String.valueOf(seq));

		byte opt = frame.getOperateFlag();
		log.debug("frame.opt=" + ByteUtils.byteToHexString(opt));

		String machineAddr = frame.getMachineAddr();
		log.debug("frame.machineAddr=" + machineAddr);

		byte busType = frame.getBusType();
		log.debug("frame.busType=" + ByteUtils.byteToHexString(busType));

		byte rotateStatus = frame.getRotateStatus();
		log.debug("frame.rotateStatus=" + ByteUtils.byteToHexString(rotateStatus));

		byte switchStatus = frame.getSwitchStatus();
		log.debug("frame.switchStatus=" + ByteUtils.byteToHexString(switchStatus));

		byte result = frame.getResult();
		log.debug("frame.result=" + ByteUtils.byteToHexString(result));

		// 帧头字节数
		int frameHeadLength = (1 + 1 + 8) + (1 + 1 + 4) + (1 + 1 + 1) + (1 + 1 + 8);

		byte[] srcArr = null;
		switch (opt) {
		case 0x01:// 电机向服务器发送状态
			if (busType == (byte) 0x01) {// 请求帧
				content = new byte[frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1) + (1 + 1 + 1)];
				// 电机地址(32字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB1 }, new byte[] { (byte) 0x20 },
						machineAddr.getBytes());
				log.debug("加入的电机地址tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 旋转状态(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB3 }, new byte[] { (byte) 0x01 },
						new byte[] { rotateStatus });
				log.debug("加入的旋转状态tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 开关状态(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB4 }, new byte[] { (byte) 0x01 },
						new byte[] { switchStatus });
				log.debug("加入的开关状态tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1),
						srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

			} else if (busType == (byte) 0x02) {// 响应帧
				content = new byte[frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1)];
				// 电机地址(32字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB1 }, new byte[] { (byte) 0x20 },
						machineAddr.getBytes());
				log.debug("加入的电机地址tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 处理结果(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB5 }, new byte[] { (byte) 0x01 },
						new byte[] { result });
				log.debug("加入的处理结果tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

			}

			break;

		case 0x02:// 服务器向电机查询状态
			if (busType == (byte) 0x01) {// 请求帧
				content = new byte[frameHeadLength + (1 + 1 + 1)];

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

			} else if (busType == (byte) 0x02) {// 响应帧
				content = new byte[frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1) + (1 + 1 + 1)
						+ (1 + 1 + 1)];
				// 电机地址(32字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB1 }, new byte[] { (byte) 0x20 },
						machineAddr.getBytes());
				log.debug("加入的电机地址tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 旋转状态(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB3 }, new byte[] { (byte) 0x01 },
						new byte[] { rotateStatus });
				log.debug("加入的旋转状态tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 开关状态(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB4 }, new byte[] { (byte) 0x01 },
						new byte[] { switchStatus });
				log.debug("加入的开关状态tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1),
						srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 处理结果(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB5 }, new byte[] { (byte) 0x01 },
						new byte[] { result });
				log.debug("加入的处理结果tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1)
						+ (1 + 1 + 1), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));
			}
			break;

		case 0x03:// 服务器远程控制电机工作状态：远程开
		case 0x04:// 服务器远程控制电机工作状态：远程关
		case 0x05:// 服务器远程控制电机工作状态：远程正转
		case 0x06:// 服务器远程控制电机工作状态：远程反转
			if (busType == (byte) 0x01) {// 请求帧
				content = new byte[frameHeadLength + (1 + 1 + 1)];

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));
			} else if (busType == (byte) 0x02) {// 响应帧
				content = new byte[frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1) + (1 + 1 + 1)];
				// 电机地址(32字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB1 }, new byte[] { (byte) 0x20 },
						machineAddr.getBytes());
				log.debug("加入的电机地址tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength, srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 操作标识(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB2 }, new byte[] { (byte) 0x01 }, new byte[] { opt });
				log.debug("加入的操作标识tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

				// 处理结果(1字节)
				srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xB5 }, new byte[] { (byte) 0x01 },
						new byte[] { result });
				log.debug("加入的处理结果tlv=" + ByteUtils.byteArrToHexString(srcArr));

				System.arraycopy(srcArr, 0, content, frameHeadLength + (1 + 1 + 32) + (1 + 1 + 1), srcArr.length);
				log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));
			}
			break;

		default:
			throw new RuntimeException("未能找到响应的操作标识:" + ByteUtils.byteArrToHexString(new byte[] { opt }));

		}

		// /////////////////设置帧头///////////////////
		// 帧序列号(8字节)
		srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xA1 }, new byte[] { (byte) 0x08 },
				ByteUtils.longToByteArray(seq));
		log.debug("seq=" + seq);
		log.debug("加入的帧序列号tlv=" + ByteUtils.byteArrToHexString(srcArr));

		System.arraycopy(srcArr, 0, content, 0, srcArr.length);
		log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

		// 帧总长度(4字节)
		srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xA2 }, new byte[] { (byte) 0x04 },
				ByteUtils.intToByteArray(content.length));
		log.debug("content.length=" + content.length);
		log.debug("加入的帧总长度tlv=" + ByteUtils.byteArrToHexString(srcArr));

		System.arraycopy(srcArr, 0, content, 1 + 1 + 8, srcArr.length);
		log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

		// 业务类型(1字节)
		srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xA3 }, new byte[] { (byte) 0x01 }, new byte[] { busType });
		log.debug("busType=" + ByteUtils.byteArrToHexString(new byte[] { (byte) 0x01 }));
		log.debug("加入的业务类型tlv=" + ByteUtils.byteArrToHexString(srcArr));

		System.arraycopy(srcArr, 0, content, (1 + 1 + 8) + (1 + 1 + 4), srcArr.length);
		log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));

		// CRC32校验和(8字节)
		long crc32 = ByteUtils.byteArrCRC32Value(content);
		byte[] crc32bt = ByteUtils.longToByteArray(crc32);
		log.debug("crc32bt=" + ByteUtils.byteArrToHexString(crc32bt));
		srcArr = TlvUtils.buildTlv(new byte[] { (byte) 0xA4 }, new byte[] { (byte) 0x08 }, crc32bt);
		log.debug("加入的CRC32校验和tlv=" + ByteUtils.byteArrToHexString(srcArr));

		System.arraycopy(srcArr, 0, content, (1 + 1 + 8) + (1 + 1 + 4) + (1 + 1 + 1), srcArr.length);
		log.debug("content tlv=" + ByteUtils.byteArrToHexString(content));
		log.debug("将对象转换为帧数组ok!");
		return content;
	}
}
