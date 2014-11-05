package com.dahuangit.iots.perception.tcpserver.pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.dahuangit.util.log.Log4jUtils;

/**
 * 客户端响应池
 * 
 * @author 黄仁良
 * 
 */
public class ClientResponsePool {

	private static Logger log = Log4jUtils.getLogger(ClientResponsePool.class);

	private ConcurrentMap<String, ClientResponse> ClientResponseMap = new ConcurrentHashMap<String, ClientResponse>();

	private ClientResponsePool() {
		new ClientResponsePoolMaintenance();
	}

	private static class ClientResponsePoolHolder {
		private static final ClientResponsePool instance = new ClientResponsePool();
	}

	public static final ClientResponsePool getInstance() {
		return ClientResponsePoolHolder.instance;
	}

	/**
	 * 将客户端响应添加到响应池
	 * 
	 * @param key
	 * @param clientResponse
	 */
	public void addClientResponse(String key, ClientResponse clientResponse) {
		Validate.notNull(key, "key 不能为空");
		Validate.notNull(clientResponse, "clientResponse 不能为空");

		this.ClientResponseMap.put(key, clientResponse);
	}

	/**
	 * 将客户端响应从响应池移除
	 * 
	 * @param key
	 */
	public void removeClientResponse(String key) {
		Validate.notNull(key, "key 不能为空");

		this.ClientResponseMap.remove(key);
	}

	/**
	 * 通过sessionId清楚客户端响应
	 * 
	 * @param sessionId
	 */
	public void removeClientResponseBySessionId(long sessionId) {
		for (Map.Entry<String, ClientResponse> entry : this.ClientResponseMap.entrySet()) {
			ClientResponse clientResponse = entry.getValue();

			IoSession ioSession = clientResponse.getIoSession();
			if (sessionId == ioSession.getId()) {
				this.ClientResponseMap.remove(clientResponse.getSeq());
			}
		}

	}

	/**
	 * 从客户端响应池中获取客户端响应
	 * 
	 * @param sessionKey
	 * @return
	 */
	public ClientResponse getClientResponse(String key) {
		Validate.notNull(key, "key 不能为空");

		return this.ClientResponseMap.get(key);
	}

	/** 响应池维护线程 定时清除掉响应池中超时的响应 */
	private class ClientResponsePoolMaintenance implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					// 每隔60分钟清理一次
					TimeUnit.SECONDS.sleep(60 * 60);

					for (Map.Entry<String, ClientResponse> entry : ClientResponseMap.entrySet()) {
						String responseKey = entry.getKey();
						ClientResponse response = entry.getValue();

						long lastCommTime = response.getUpTime();
						long now = System.currentTimeMillis();

						// 超时时间，默认3小时
						long timeout = 3 * 60 * 60 * 60;

						if ((now - lastCommTime) > timeout) {
							removeClientResponse(responseKey);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
