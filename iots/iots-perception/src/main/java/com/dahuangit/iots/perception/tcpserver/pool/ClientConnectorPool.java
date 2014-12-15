package com.dahuangit.iots.perception.tcpserver.pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

/**
 * 客户端连接池,服务器端向客户端发送消息时用
 * 
 * @author 黄仁良
 * 
 */
public class ClientConnectorPool {

	private static final Logger log = Logger.getLogger(ClientConnectorPool.class);

	/** 当前所有终端连接 */
	private ConcurrentMap<String, ClientConnector> clientConnectorMap = new ConcurrentHashMap<String, ClientConnector>();

	private ClientConnectorPool() {
		new ClientConnectorPoolMaintenance();
	}

	private static class ClientConnectorPoolHolder {
		private static final ClientConnectorPool instance = new ClientConnectorPool();
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static final ClientConnectorPool getInstance() {
		return ClientConnectorPoolHolder.instance;
	}

	/**
	 * 将客户端连接添加到连接池
	 * 
	 * @param key
	 * @param clientConnector
	 */
	public void addClientConnector(String key, ClientConnector clientConnector) {
		Validate.notEmpty(key, "key 不能为空");
		Validate.notNull(clientConnector, "clientConnector 不能为空");

		this.clientConnectorMap.put(key, clientConnector);
	}

	/**
	 * 将客户端连接从连接池移除
	 * 
	 * @param key
	 */
	public void removeClientConnector(String key) {
		Validate.notEmpty(key, "key 不能为空");

		this.clientConnectorMap.remove(key);
	}

	/**
	 * 通过sessionId清楚客户端连接
	 * 
	 * @param sessionId
	 */
	public void removeClientConnectorBySessionId(long sessionId) {
		for (Map.Entry<String, ClientConnector> entry : clientConnectorMap.entrySet()) {
			ClientConnector connector = entry.getValue();

			IoSession ioSession = connector.getIoSession();
			if (sessionId == ioSession.getId()) {
				this.clientConnectorMap.remove(connector.getClientKey());
			}
		}

	}

	/**
	 * 从客户端连接池中获取客户端连接
	 * 
	 * @param sessionKey
	 * @return
	 */
	public ClientConnector getClientConnector(String key) {
		Validate.notEmpty(key, "key 不能为空");

		return this.clientConnectorMap.get(key);
	}

	/**
	 * 是否含有客户端
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsClientConnector(String key) {
		ClientConnector c = this.getClientConnector(key);

		if (null == c) {
			return false;
		}

		return true;
	}

	/** 连接池维护线程 定时清除掉连接池中超时的连接 */
	private class ClientConnectorPoolMaintenance implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					// 每隔60分钟清理一次
					TimeUnit.SECONDS.sleep(60 * 60);

					for (Map.Entry<String, ClientConnector> entry : clientConnectorMap.entrySet()) {
						String connectionKey = entry.getKey();
						ClientConnector connection = entry.getValue();

						long lastCommTime = connection.getLastCommTime();
						long now = System.currentTimeMillis();

						// 超时时间，默认3小时
						long timeout = 3 * 60 * 60 * 60;

						if ((now - lastCommTime) > timeout) {
							removeClientConnector(connectionKey);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
