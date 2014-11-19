package com.dahuangit.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.castor.CastorMarshaller;

import com.dahuangit.base.dto.oxm.response.OxResponse;
import com.dahuangit.util.xml.XmlUtils;

public abstract class BaseController {

	@Autowired
	protected CastorMarshaller xmlMarshaller = null;

	protected String responseToXml(OxResponse response) {
		String responseStr = null;
		try {
			responseStr = XmlUtils.obj2xml(xmlMarshaller, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseStr;
	}

	protected Object xmlToRequest(String xml, Class clazz) throws Exception {
		return XmlUtils.xml2obj(xmlMarshaller, xml, clazz);
	}

}
