package com.dahuangit.util.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.Validate;

/**
 * 
 * @author 黄仁良
 * 
 */
public class BeanUtils2 extends BeanUtils {
	/**
	 * 将bean转换为Map<String,Object> <br>
	 * 其中map的key为属性名称，value为属性值
	 * 
	 * @param clazz
	 * @return
	 */
	public static Map<String, String> bean2Map(Object obj) {
		Validate.notNull(obj, "传入对象不能为null");
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = obj.getClass().getDeclaredFields();
		
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), String.valueOf(field.get(obj)));
				field.setAccessible(false);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return map;
	}
}
