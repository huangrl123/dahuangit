package com.dahuangit.util.bean.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dto {

	/**
	 * Dto所对应Model的名称集合.
	 * 
	 * @return
	 */
	String[] sourceModelName() default {};

	/**
	 * Dto所对应的Model的class名称集合.
	 * 
	 * @return
	 */
	Class<?>[] sourceModelClass() default {};

	/**
	 * ID所对应的名称集合.
	 * 
	 * @return
	 */
	String[] idField() default {};

	/**
	 * 是否忽略没有注解的field.
	 * 
	 * @return
	 */
	boolean ignoreUnannoatedField() default false;
}
