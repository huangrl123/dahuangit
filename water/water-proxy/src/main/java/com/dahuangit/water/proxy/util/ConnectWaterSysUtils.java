package com.dahuangit.water.proxy.util;

import org.apache.log4j.Logger;
import org.springframework.oxm.castor.CastorMarshaller;

import com.dahuangit.base.dto.Response;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class ConnectWaterSysUtils {

	private static final Logger log = Log4jUtils.getLogger(ConnectWaterSysUtils.class);

	public static Response connect(String url, CastorMarshaller xmlMarshaller, Class clazz) throws Exception {
		Response response = new Response();

		try {
			log.debug("本代理程序接收到app的请求，请求url为:");
			log.debug(url);

			String xml = HttpKit.getHttpRequestContent(url, "UTF-8");

			log.debug("本代理程序接收到热水管理平台的响应");
			log.debug("app的请求url为:");
			log.debug(url);
			log.debug("本代理程序收到热水管理平台的响应报文为:");
			log.debug(XmlUtils.formatXMLStr(xml));

			Object obj = XmlUtils.xml2obj(xmlMarshaller, xml, clazz);

			response = (Response) obj;
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			
			log.debug("请求报错，app请求的url为：");
			log.debug(url);
			
			log.debug("报错信息：");
			e.printStackTrace();
			throw e;
		}

		return response;
	}
}
