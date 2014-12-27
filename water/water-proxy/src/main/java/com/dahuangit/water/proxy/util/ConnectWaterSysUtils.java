package com.dahuangit.water.proxy.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.oxm.castor.CastorMarshaller;

import com.dahuangit.base.dto.Request;
import com.dahuangit.base.dto.Response;
import com.dahuangit.util.bean.BeanUtils2;
import com.dahuangit.util.net.http.HttpKit;
import com.dahuangit.util.xml.XmlUtils;

public class ConnectWaterSysUtils {

	public static Response connect(Request request, String url, CastorMarshaller xmlMarshaller, Class clazz) throws Exception {
		Response response = new Response();

		Map<String, String> params = null;
		if (null != request) {
			params = BeanUtils2.bean2Map(request);
		}

		else {
			params = new HashMap<String, String>();
		}

		try {
			String xml = HttpKit.getHttpRequestContent(url, params, "UTF-8");
			response = XmlUtils.xml2obj(xmlMarshaller, xml, clazz);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
			e.printStackTrace();
			throw e;
		}

		return response;
	}
}
