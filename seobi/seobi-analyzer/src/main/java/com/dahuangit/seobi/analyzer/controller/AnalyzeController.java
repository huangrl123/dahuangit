package com.dahuangit.seobi.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.oxm.response.OxResponse;
import com.dahuangit.seobi.analyzer.dto.oxm.response.DoAnalyzeResponse;
import com.dahuangit.seobi.analyzer.service.AnalyzeService;

@Controller
@RequestMapping(value = "/analyze", method = RequestMethod.POST)
public class AnalyzeController extends BaseController {

	@Autowired
	private AnalyzeService analyzeService = null;

	@RequestMapping(value = "/doAnalyze")
	@ResponseBody
	public String doAnalyze(String content) throws Exception {
		DoAnalyzeResponse response = new DoAnalyzeResponse();

		try {
			double percent = this.analyzeService.getBaiduOriginarityPercent(content);
			response.setOriginarityPercent(percent);
		} catch (Exception e) {
			response.setSuccess(false);
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}

	@RequestMapping(value = "/analyzeShuoshuoBaiduOriginarityPercent")
	@ResponseBody
	public String analyzeShuoshuoBaiduOriginarityPercent(String content) throws Exception {
		OxResponse response = new OxResponse();

		try {
			this.analyzeService.analyzeQQTalkMsgBaiduOriginatyPercent();
		} catch (Exception e) {
			response.setSuccess(false);
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}
}
