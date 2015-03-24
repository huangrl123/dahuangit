package com.dahuangit.iots.pcserver.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dahuangit.base.controller.BaseController;
import com.dahuangit.util.date.DateUtils;

/**
 * pc端首页面框架相关的处理器
 * 
 * @author 大黄
 * 
 *         2015年3月23日上午11:05:04
 */
@Controller
@RequestMapping(value = "/frame")
public class FrameController extends BaseController {

	/**
	 * 跳转到main页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPage() {
		return "pc/frame/main";
	}

	/**
	 * 跳转到head页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/head", method = RequestMethod.GET)
	public String headPage() {
		return "pc/frame/head";
	}

	/**
	 * 跳转到body页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/body", method = RequestMethod.GET)
	public String bodyPage(ModelMap map) {
		Date d = new Date();
		String dateStr = DateUtils.format(d, "yyyy年MM月dd日");
		String wkStr = DateUtils.getWeekOfDate(d);
		String s = dateStr + " " + wkStr;
		map.put("today", s);
		return "pc/frame/body";
	}

	/**
	 * 跳转到leftMenu页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/leftMenu", method = RequestMethod.GET)
	public String leftMenuPage() {
		return "pc/frame/leftMenu";
	}

	/**
	 * 跳转到rightContent页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/rightContent", method = RequestMethod.GET)
	public String rightContentPage() {
		return "pc/rightContent";
	}

	/**
	 * 跳转到page2页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/page2", method = RequestMethod.GET)
	public String page2() {
		return "pc/page2";
	}
}
