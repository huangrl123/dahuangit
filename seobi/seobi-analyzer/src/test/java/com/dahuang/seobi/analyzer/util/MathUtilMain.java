package com.dahuang.seobi.analyzer.util;

import org.apache.log4j.Logger;

import com.dahuangit.util.log.Log4jUtils;

public class MathUtilMain {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			MathUtilMain.class);

	public static void main(String[] args) {
		double d = (double)5477 / (double)38;
		int sectionCount = (int) Math.ceil(d);

		log.debug("d=" + d);

		log.debug("sectionCount=" + sectionCount);
	}

}
