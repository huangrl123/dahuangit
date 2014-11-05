/**
 * BeanUtils.java
 * 
 * 深圳凯莱特电子科技股份有限公司版权所有
 * Copyright 2010 Knet Co.,Ltd. All rights reserved.
 */
package com.dahuangit.util.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;

/**
 * 提供常见的bean操作
 *
 * @author wlh
 * 
 * 创建于 2010-7-2 下午03:25:03 
 */
public abstract class BeanUtils {

    /**
     * 重写了Spring的BeanUtils的同名方法，提供了当属性值相同时，不执行copy的逻辑。
     * 
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties  需要忽略的字段
     * 
     * @return 返回true代表有属性被复制，否则除了忽略属性外，这两个对象完全相同
     * @throws BeansException
     */
    public static boolean copyProperties(Object source, Object target, String[] ignoreProperties)
            throws BeansException {

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
                            if(value == targetValue || (value != null && value.equals(targetValue))) {
                                continue;   // 源值与目标值相同，跳过copy
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
