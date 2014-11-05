package com.dahuangit.seobi.receiver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.oxm.response.OxResponse;
import com.dahuangit.seobi.receiver.dto.oxm.request.QQTalkMsgsReqXml;
import com.dahuangit.seobi.receiver.service.QQTalkMsgService;
import com.dahuangit.util.xml.XmlUtils;

/**
 * 数据接收服务
 * 
 * @author 黄仁良
 * 
 */
@Controller
@RequestMapping("/receiver")
public class DataReceiverController extends BaseController {

	protected final Logger LOG = Logger.getLogger(getClass());

	@Autowired
	private QQTalkMsgService qqTalkMsgService = null;

	/**
	 * 接收qq说说信息服务
	 * 
	 * @param qqTalDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receiveQQTalkMsg", method = RequestMethod.POST)
	@ResponseBody
	public String receiveQQTalkMsg(String qqTalkMsgs) throws Exception {
		this.LOG.debug("qq说说信息接收服务响应报文：" + XmlUtils.formatXMLStr(qqTalkMsgs));
		
		OxResponse resp = new OxResponse();

		try {
			QQTalkMsgsReqXml qqTalkMsgsReqXml = XmlUtils.xml2obj(xmlMarshaller, qqTalkMsgs, QQTalkMsgsReqXml.class);
			qqTalkMsgService.addQQTalkMsg(qqTalkMsgsReqXml);

		} catch (Exception e) {
			resp.setSuccess(false);
			resp.setMsg(e.getMessage());
			e.printStackTrace();
		}

		String responseStr = XmlUtils.obj2xml(xmlMarshaller, resp);
        
		this.LOG.debug("qq说说信息接收服务响应报文：" + responseStr);
		
		return responseStr;
	}
}
