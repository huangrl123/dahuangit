package com.dahuangit.iots.pcserver.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dahuangit.base.controller.BaseController;

@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

	@Value("${app.apk.dir}")
	private String apkFileDir = null;

	@RequestMapping(value = "/toAppDownloadPage", method = RequestMethod.GET)
	public String toAppDownloadPage(ModelMap map) {
		File file = new File(apkFileDir);
		File[] f = file.listFiles();
		map.put("apkName", f[0].getName());
		return "/pc/app/downloadApp";
	}

}
