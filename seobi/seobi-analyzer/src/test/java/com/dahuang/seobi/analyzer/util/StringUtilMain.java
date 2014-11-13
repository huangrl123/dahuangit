package com.dahuang.seobi.analyzer.util;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;

public class StringUtilMain {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			StringUtilMain.class);

	public static void main(String[] args) {
		String s = "刚在楼下吃了一会儿小米粥，就这一会功夫，就抓到了一只跳蚤";
		
	    String[] arr = s.split("一会功夫1");
	    
	    log.debug(s.indexOf("吃了"));
	}

}
