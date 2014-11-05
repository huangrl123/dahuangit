/**
 * CloneUtil.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2011 Shenzhen KNet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * 克隆工具类<br>
 * 本工具类提供的方法全为深克隆方法，即克隆对象所有基本类型和引用类型的所有属性和方法
 * 
 * @author 黄仁良
 * 
 *         创建于 2011-5-26 上午09:35:09
 */
public class CloneUtils {
	/**
	 * 按照原对象克隆一个对象
	 * 
	 * @param obj
	 *            原始对象
	 * @return
	 */
	public static Object cloneObj(Object obj) throws Exception {
		Validate.notNull(obj, "传入的对象不能为空");
		Object o = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			o = ois.readObject();
		} finally {
			bos.close();
			oos.close();
			bis.close();
			ois.close();
		}
		return o;
	}

	/**
	 * 根据原始对象克隆一组对象
	 * 
	 * @param obj
	 *            原始对象
	 * @param num
	 *            需要克隆的份数
	 * @return
	 */
	public static List<?> cloneObj(Object obj, int num) throws Exception {
		Validate.notNull(obj, "传入的对象不能为空");
		if (num < 1) {
			throw new IllegalArgumentException("需要克隆的份数必须大于等于1");
		}
		List<Object> objs = new ArrayList<Object>();
		for (int i = 0; i < num; i++) {
			objs.add(CloneUtils.cloneObj(obj));
		}
		return objs;
	}
}
