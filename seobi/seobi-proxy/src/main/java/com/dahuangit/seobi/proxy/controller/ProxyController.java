package com.dahuangit.seobi.proxy.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.oxm.response.OxResponse;
import com.dahuangit.seobi.proxy.dto.oxm.request.DoRequestByProxyRequest;
import com.dahuangit.seobi.proxy.dto.oxm.response.DoRequestByProxyResponse;
import com.dahuangit.seobi.proxy.service.ProxyService;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpHeaderInfo;

@Controller
@RequestMapping("/proxy")
public class ProxyController extends BaseController {

	private static final Logger log = Log4jUtils.getLogger(ProxyController.class);

	@Autowired
	private ProxyService proxyService = null;

	@RequestMapping(value = "/doRequestByProxy", method = RequestMethod.POST)
	@ResponseBody
	public String doRequestByProxy(String requestStr) throws Exception {
		DoRequestByProxyRequest doRequestByProxyRequest = (DoRequestByProxyRequest) this.xmlToRequest(requestStr,
				DoRequestByProxyRequest.class);

		DoRequestByProxyResponse opResponse = new DoRequestByProxyResponse();
		String content = null;

		try {
			HttpHeaderInfo headerInfo = new HttpHeaderInfo();
			headerInfo.setHost(doRequestByProxyRequest.getUrl());
			headerInfo.setMethod(doRequestByProxyRequest.getMethod());

			content = this.proxyService.doRequestByProxy(headerInfo);

			opResponse.setContent("<![CDATA[" + content + "]]>");
			opResponse.setSuccess(true);
		} catch (Exception e) {
			opResponse.setSuccess(false);
			e.printStackTrace();
		}

		return this.responseToXml(opResponse);
	}

	@RequestMapping(value = "/doPostMethodTest", method = RequestMethod.POST)
	@ResponseBody
	public String doPostMethodTest() throws Exception {
		OxResponse response = new OxResponse();

		return this.responseToXml(response);
	}

	@RequestMapping(value = "/doGetMethodTest", method = RequestMethod.GET)
	@ResponseBody
	public String doGetMethodTest() throws Exception {
		OxResponse response = new OxResponse();

		return this.responseToXml(response);
	}
}
