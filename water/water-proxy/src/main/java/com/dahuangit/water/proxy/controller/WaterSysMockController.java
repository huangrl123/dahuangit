package com.dahuangit.water.proxy.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.water.proxy.service.WaterService;

@Controller
@RequestMapping("/energyswot")
public class WaterSysMockController extends BaseController {

	@Autowired
	private WaterService waterService = null;

	private String basePath = "E:/dahuang-workspace/dahuangit/water/water-proxy/src/test/resources/";

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/webapp/one/query.action", method = RequestMethod.GET)
	@ResponseBody
	public String login(HttpServletRequest request) throws IOException {
		String query = request.getParameter("query");
		
		if("login".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "success.xml"));
		}
		
		else if("inoutmoney".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "shouzhi.xml"));
		}
		
		else if("projectlist".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "projectList.xml"));
		}
		
		else if("devwarning".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "yujing.xml"));
		}
		
		else if("querysy".equals(query)) {
			String actionType = request.getParameter("qrytype");
			if("1".equals(actionType)) {
				return FileUtils.readFileToString(new File(basePath + "xueqigaikuang.xml"));
			}
			
			else if("2".equals(actionType)) {
				return FileUtils.readFileToString(new File(basePath + "xueqimonth.xml"));
			}
			
			else if("3".equals(actionType)) {
				return FileUtils.readFileToString(new File(basePath + "xueqi3year.xml"));
			}
		}
		
		else if("areabuildlist".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "ld.xml"));
		}
		
		else if("buildrecentdata".equals(query)) {
			return FileUtils.readFileToString(new File(basePath + "yongshuiRecord.xml"));
		}
		
		else {
			return FileUtils.readFileToString(new File(basePath + "success.xml"));
		}
		
		return null;
	}

}
