package com.dahuangit.seobi.proxy.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.opm.request.OpRequest;
import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.proxy.dto.opm.response.ProxyResponse;
import com.dahuangit.seobi.proxy.service.ProxyService;
import com.dahuangit.util.log.Log4jUtils;

@Controller
@RequestMapping("/proxyMgr")
public class ProxyMgrController extends BaseController {

	private static final Logger log = Log4jUtils.getLogger(ProxyMgrController.class);

	@Autowired
	private ProxyService proxyService = null;

	@RequestMapping(value = "/impProxy", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse impProxy(MultipartFile multipartFile) {
		OpResponse opResponse = new OpResponse();

		try {
			proxyService.impProxy(multipartFile);
			opResponse.setSuccess(true);
		} catch (Exception e) {
			opResponse.setSuccess(false);
			opResponse.setMsg(e.getMessage());

			e.printStackTrace();
		}

		return opResponse;
	}

	@RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public PageQueryResult<ProxyResponse> findByPage(OpRequest opRequest) {
		PageQueryResult<ProxyResponse> result = this.proxyService
				.findByPage(opRequest.getStart(), opRequest.getLimit());
		return result;
	}

	@RequestMapping(value = "/deleteProxy", method = RequestMethod.POST)
	@ResponseBody
	public OpResponse deleteProxy(Integer id) {
		OpResponse opResponse = new OpResponse();

		try {
			this.proxyService.deleteProxy(id);
			opResponse.setSuccess(true);
		} catch (Exception e) {
			opResponse.setSuccess(false);
			opResponse.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return opResponse;
	}
}
