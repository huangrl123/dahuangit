package com.dahuangit.util.net;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * telnet相关的工具包
 * 
 * @author 黄仁良
 * 
 */
public class TelnetUtils {

	/**
	 * 是否为可用的服务器
	 * 
	 * @param hostname
	 * @param port
	 * @param connectTimeout
	 * @return
	 */
	public static boolean isAvailableServer(String hostname, int port, int connectTimeout) {
		try {
			TelnetClient telnetClient = new TelnetClient();
			telnetClient.setConnectTimeout(connectTimeout);
			telnetClient.connect(hostname, port);

			return true;
		} catch (SocketException e) {

			return false;
		} catch (IOException e) {

			return false;
		}

	}

}
