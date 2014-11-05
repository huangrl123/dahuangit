package com.dahuangit.util.bean.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DtoField
 * 
 * @author 黄仁良
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoField {

	/**
	 * 对应model的字段名称，允许为nested.
	 * 
	 * @return
	 */
	String field() default "";

	/**
	 * 是否忽略该字段的merge以及build.
	 * 
	 * @return
	 */
	boolean ignore() default false;

	/**
	 * 是否在merge过程中忽略这个字段.
	 * 
	 * @return
	 */
	boolean skipMerge() default false;

	/**
	 * 字段是否有版本.
	 * 
	 * @return
	 */
	boolean version() default false;

	/**
	 * 对应Model的类名.
	 * 
	 * @return
	 */
	String sourceModel() default "";
}
