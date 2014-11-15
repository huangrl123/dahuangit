package com.dahuangit.seobi.analyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.oxm.response.OxResponse;
import com.dahuangit.seobi.analyzer.dto.oxm.response.DoAnalyzeResponse;
import com.dahuangit.seobi.analyzer.service.AnalyzeService;
import com.dahuangit.util.NumberUtils;

@Controller
@RequestMapping(value = "/analyze", method = RequestMethod.POST)
public class AnalyzeController extends BaseController {

	@Autowired
	private AnalyzeService analyzeService = null;

	/**
	 * 分析任意字符的原创度
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 分析并且保存qq说说的原创度
	 * 
	 * @param idsStr
	 *            多个id以逗号分隔
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/analyzeShuoshuoBaiduOriginarityPercent")
	@ResponseBody
	public String analyzeShuoshuoBaiduOriginarityPercent(String idsStr) throws Exception {
		OxResponse response = new OxResponse();

		try {
			List<Integer> idList = NumberUtils.ids2integerList(idsStr);
			this.analyzeService.analyzeAndSaveQQTalkMsgBaiduOriginatyPercent(idList);
		} catch (Exception e) {
			response.setSuccess(false);
			e.printStackTrace();
		}

		return this.responseToXml(response);
	}
}
