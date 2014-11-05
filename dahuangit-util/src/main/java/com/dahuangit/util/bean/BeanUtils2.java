package com.dahuangit.util.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;

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
	public static Map<String, Object> bean2Map(Object obj) {
		Validate.notNull(obj, "传入对象不能为null");
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
				field.setAccessible(false);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return map;
	}

	/**
	 * 重写了Spring的BeanUtils的同名方法，提供了当属性值相同时，不执行copy的逻辑。
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 * @param ignoreProperties
	 *            需要忽略的字段
	 * 
	 * @return 返回true代表有属性被复制，否则除了忽略属性外，这两个对象完全相同
	 * @throws BeansException
	 */
	public static boolean copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();

		boolean isDiff = false;

		PropertyDescriptor[] targetPds = PropertyUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null
					&& (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				try {
					PropertyDescriptor sourcePd = PropertyUtils.getPropertyDescriptor(source, targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						try {
							Method srcReadMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(srcReadMethod.getDeclaringClass().getModifiers())) {
								srcReadMethod.setAccessible(true);
							}
							Object value = srcReadMethod.invoke(source);

							// 检查目标值是否和源值相同
							Method targetReadMethod = targetPd.getReadMethod();
							if (!Modifier.isPublic(targetReadMethod.getDeclaringClass().getModifiers())) {
								targetReadMethod.setAccessible(true);
							}
							Object targetValue = targetReadMethod.invoke(target);
							if (value == targetValue || (value != null && value.equals(targetValue))) {
								continue; // 源值与目标值相同，跳过copy
							}

							Method targetWriteMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers())) {
								targetWriteMethod.setAccessible(true);
							}
							targetWriteMethod.invoke(target, value);
							isDiff = true;
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				}
			}
		}

		return isDiff;
	}
}
