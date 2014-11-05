package com.dahuangit.util.log;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j相关的工具类
 * 
 * @author 黄仁良
 * 
 */
public class Log4jUtils {
	/**
	 * 获取到Logger对象
	 * 
	 * @param log4jPropertiesFile
	 *            log4j配置文件
	 *            例如:E:\\dahuang_workspace\\seobi\\seobi-webapp\\src\\test\\
	 *            resources\\log4j.properties
	 * @param clazz
	 *            当前类
	 *            例如：DataReceiverControllerTest.class
	 * @return
	 */
	public static Logger getLogger(String log4jPropertiesFile, Class clazz) {
		Validate.notNull(log4jPropertiesFile, " log4j配置文件不能为null");
		Validate.notNull(clazz, "class类不能为null");
		
		PropertyConfigurator.configure(log4jPropertiesFile);
		
		Logger logger = Logger.getLogger(clazz);
		
		return logger;
	}
	
	/**
	 * 获取到Logger对象
	 * 
	 * @param clazz
	 *            当前类
	 *            例如：DataReceiverControllerTest.class
	 * @return
	 */
	public static Logger getLogger(Class clazz) {
		Validate.notNull(clazz, "class类不能为null");

		Logger logger = Logger.getLogger(clazz);

		return logger;
	}
	
}
