package com.dahuangit.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

public class JsonUtil {
	// 任意对象转换成json
	public static String object2Json(Object object) {
		StringBuffer json = new StringBuffer();
		if (object == null) {
			json.append("\"\"");
		} else if (object instanceof String) {
			json.append("\"").append((String) object).append("\"");
		} else if (object instanceof Integer) {
			json.append("\"").append((Integer) object).append("\"");
		} else if (object instanceof Long) {
			json.append("\"").append((Long) object).append("\"");
		} else if (object instanceof String[]) {
			json.append("[");
			String[] strArray = (String[]) object;
			if (null != strArray && strArray.length > 0) {
				for (Object str : strArray) {
					json.append(str);
					json.append(",");
				}
				json.setCharAt(json.length() - 1, ']');
			} else {
				json.append("]");
			}
		} else {
			json.append(bean2Json(object));
		}
		return json.toString();
	}

	// javaBean转换成json
	public static String bean2Json(Object bean) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(),
					Object.class);
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					Object name = props[i].getName();
					Object value = props[i].getReadMethod().invoke(bean);
					if(value != null){
						json.append(object2Json(name));
						json.append(":");
						json.append(object2Json(value));
						json.append(",");
					}
				}
				json.setCharAt(json.length() - 1, '}');

			} else {
				json.append("}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	// list转换成json
	public static String list2Json(List<?> list) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"rows\":");
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object object : list) {
				json.append(bean2Json(object));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}
}
