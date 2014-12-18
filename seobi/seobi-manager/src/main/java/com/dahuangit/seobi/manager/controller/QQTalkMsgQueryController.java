package com.dahuangit.seobi.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.base.dto.PagingRequest;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.manager.dto.response.QQTalkMsgResponse;
import com.dahuangit.seobi.manager.dto.response.RelatedSearchKeyResponse;
import com.dahuangit.seobi.manager.service.QQTalkMsgQueryService;

@Controller
@RequestMapping("/qQTalkMsgQueryController")
public class QQTalkMsgQueryController extends BaseController {

	@Autowired
	private QQTalkMsgQueryService qqTalkMsgQueryService = null;

	@RequestMapping(value = "/findQQTalkMsgByPage", method = RequestMethod.GET)
	public String findQQTalkMsgByPage(ModelMap modelMap, PagingRequest request) {
		PageQueryResult<QQTalkMsgResponse> pr = null;

		try {
			
			pr = this.qqTalkMsgQueryService.findByPage(request.getPage().getStart(), request.getPage().getLimit());
			modelMap.put("QQTalkMsgPageQueryResult", pr);
			
			request.getPage().setTotalCount((int) pr.getTotalCount());
			request.getPage().setCurPageCount(pr.getResults().size());
			modelMap.put("page", request.getPage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "QQTalkList";
	}

	@RequestMapping(value = "/getRelatedSearchKeysByQQTalkMsgId", method = RequestMethod.GET)
	public String getRelatedSearchKeysByQQTalkMsgId(ModelMap modelMap, Integer qQTalkMsgId) {

		List<RelatedSearchKeyResponse> list = null;

		try {
			list = this.qqTalkMsgQueryService.getRelatedSearchKeysByQQTalkMsgId(qQTalkMsgId);
			QQTalkMsgResponse response = this.qqTalkMsgQueryService.findQQTalkMsg(qQTalkMsgId);
			modelMap.put("relatedSearchKeyResponseList", list);
			modelMap.put("response", response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "QQTalkRelatedKey";
	}

}
