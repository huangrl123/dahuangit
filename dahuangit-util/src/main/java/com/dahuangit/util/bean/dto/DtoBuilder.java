package com.dahuangit.util.bean.dto;

import java.util.Collections;
import java.util.Map;

/**
 * DTO的构建工具，提供了从DTO到MODEL间的相互转换
 * 
 * @author 黄仁良
 * 
 */
public class DtoBuilder {

	private static DtoBuilderBean instance = new DtoBuilderBean();

	// buildDto之后不调用任何listener
	private static final Map<Class<?>, BuildDtoEventListener<?>> EMPTY_BUILD_DTO_LISTENER_MAP = Collections.emptyMap();

	// mergeModel之后不调用任何listener
	private static final Map<Class<?>, MergeModelEventListener> EMPTY_MERGE_MODEL_LISTENER_MAP = Collections.emptyMap();

	/* build DTO */

	public static <E> E buildDto(Class<E> dtoClass, Object... models) {
		return buildDto(EMPTY_BUILD_DTO_LISTENER_MAP, dtoClass, models);
	}

	public static <E> E buildDto(Map<Class<?>, BuildDtoEventListener<?>> buildListener, Class<E> dtoClass,
			Object... models) {
		return instance.buildDto(buildListener, dtoClass, models);
	}

	/* merge Model */

	/**
	 * Merge指定Dto到一个Model，Model的信息从Dto的annotation中获得
	 */
	public static Object mergeModel(Object dto) {
		if (dto == null) {
			throw new IllegalArgumentException("DTO must not be null.");
		}

		return mergeModel(EMPTY_MERGE_MODEL_LISTENER_MAP, dto, null);
	}

	/**
	 * Merge指定Dto到一个Model，Model应该对应指定的sourceModelClassName
	 * 如果Dto的sourceModelClass没有对应的class，则抛出异常
	 */
	public static Object mergeModel(Object dto, String sourceModelClassName) {
		if (dto == null || sourceModelClassName == null) {
			throw new IllegalArgumentException("DTO/sourceModelClass must not be null.");
		}

		return mergeModel(EMPTY_MERGE_MODEL_LISTENER_MAP, dto, sourceModelClassName, null);
	}

	/**
	 * Merge指定Dto到一个Model
	 */
	public static Object mergeModel(Object dto, Object model) {
		if (dto == null || model == null) {
			throw new IllegalArgumentException("DTO/Model must not be null.");
		}

		return mergeModel(EMPTY_MERGE_MODEL_LISTENER_MAP, dto, model);
	}

	public static Object mergeModel(Map<Class<?>, MergeModelEventListener> map, Object dto, Object model) {
		return instance.mergeModel(map, dto, null, model); // null source model
															// class name
	}

	public static Object mergeModel(Map<Class<?>, MergeModelEventListener> map, Object dto,
			String sourceModelClassName, Object model) {
		return instance.mergeModel(map, dto, sourceModelClassName, model);
	}

	public static void mergeModels(Object dto, Object... models) {
		mergeModels(EMPTY_MERGE_MODEL_LISTENER_MAP, dto, models);
	}

	public static void mergeModels(Map<Class<?>, MergeModelEventListener> map, Object dto, Object... models) {
		instance.mergeModels(map, dto, models);
	}
}