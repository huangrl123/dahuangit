package com.dahuangit.iots.perception.tcpclient.pool;

import org.apache.log4j.Logger;

import com.dahuangit.iots.perception.tcpclient.PerceptionClient;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponse;
import com.dahuangit.iots.perception.tcpserver.pool.ClientResponsePool;
import com.dahuangit.util.log.Log4jUtils;

public class ClientResponsePoolTest {

	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\iots\\iots-webapp\\src\\test\\resources\\log4j.properties", PerceptionClient.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientResponsePool clientResponsePool = ClientResponsePool.getInstance();
		
		ClientResponse clientResponse = new ClientResponse();
		
	    long b = 2l; 
		clientResponsePool.addClientResponse(String.valueOf(b), clientResponse);
		
		log.debug("ok");
	}

}
