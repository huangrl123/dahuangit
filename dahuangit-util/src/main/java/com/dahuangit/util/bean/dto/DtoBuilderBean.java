package com.dahuangit.util.bean.dto;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Factory;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.proxy.HibernateProxy;

/**
 * DTO的构建工具实现
 * 
 * @author 黄仁良
 * 
 */
@SuppressWarnings("unchecked")
public class DtoBuilderBean {

	protected static Logger logger = Logger.getLogger(DtoBuilderBean.class);

	private HashSet<Class<?>> nonDtoClasses; // 对于非@Dto类的一个缓存

	private String defaultSourceModelNames[] = { "#0", "#1", "#2", "#3", "#4", "#5", "#6", "#7", "#8", "#9", "#10",
			"#11", "#12", "#13", "#14", "#15", "#16", "#17", "#18", "#19" };

	protected DtoBuilderBean() {
		nonDtoClasses = new HashSet<Class<?>>();
		nonDtoClasses.add(String.class);
		nonDtoClasses.add(BigDecimal.class);
		nonDtoClasses.add(BigInteger.class);
		nonDtoClasses.add(Integer.TYPE);
		nonDtoClasses.add(Character.TYPE);
		nonDtoClasses.add(Boolean.TYPE);
		nonDtoClasses.add(Long.TYPE);
		nonDtoClasses.add(Double.TYPE);
		nonDtoClasses.add(Float.TYPE);
		nonDtoClasses.add(Integer.class);
		nonDtoClasses.add(Character.class);
		nonDtoClasses.add(Boolean.class);
		nonDtoClasses.add(Long.class);
		nonDtoClasses.add(Double.class);
		nonDtoClasses.add(Float.class);
		nonDtoClasses.add(Date.class);
		nonDtoClasses.add(Timestamp.class);
	}

	/********************************************* Build DTO ****************************************************/

	/**
	 * 将多个model构建成一个DTO对象
	 * 
	 * @param buildListener
	 * @param dtoClass
	 * @param models
	 * @return
	 */
	public <E> E buildDto(Map<Class<?>, BuildDtoEventListener<?>> buildListener, Class<E> dtoClass, Object models[]) {
		if (models == null || models.length == 0 || models.length == 1 && models[0] == null) {
			return null;
		}

		Dto obj = (Dto) dtoClass.getAnnotation(Dto.class);
		if (obj == null) {
			throw new DtoException(new StringBuilder().append("Class ").append(dtoClass)
					.append(" is not annotated with @DTO").toString());
		}
		HashMap srcObjMap = createSourceObjectMap(obj, models);
		E dtoInstance;
		try {
			dtoInstance = dtoClass.newInstance();
		} catch (Exception exception) {
			throw new DtoException(new StringBuilder().append("Failed to instantiate class ").append(dtoClass)
					.toString(), exception);
		}

		PropertyDescriptor propDescriptors[];
		int propLength = (propDescriptors = PropertyUtils.getPropertyDescriptors(dtoClass)).length;
		for (int j = 0; j < propLength; j++) {
			PropertyDescriptor propertydescriptor = propDescriptors[j];
			buildField(buildListener, srcObjMap, dtoInstance, propertydescriptor);
		}

		if (buildListener.containsKey(dtoClass))
			((BuildDtoEventListener) buildListener.get(dtoClass)).afterBuildDto(dtoInstance, models);
		return dtoInstance;
	}

	/**
	 * 构建一个
	 * 
	 * @param buildListener
	 * @param srcModelMap
	 * @param dto
	 * @param dtoPropDescriptor
	 */
	private void buildField(Map<Class<?>, BuildDtoEventListener<?>> buildListener, HashMap srcModelMap, Object dto,
			PropertyDescriptor dtoPropDescriptor) {
		Dto dtoAnnotation;
		if ((dtoAnnotation = getDtoAnnotation(getPropertyDeclaringClass(dtoPropDescriptor))) == null) {
			return;
		}
		if (dtoPropDescriptor.getWriteMethod() == null) {
			return;
		}
		DtoField dtofield = getDtoFieldAnnotation(dto.getClass(), dtoPropDescriptor);
		String fieldSourceModel = null;
		String field = null;

		// 两种情况下，忽略Dto的这个field
		if (dtofield != null && dtofield.ignore() || dtofield == null && dtoAnnotation.ignoreUnannoatedField()) {
			return;
		}
		if (dtofield != null) {
			if (!"".equals(dtofield.sourceModel()))
				fieldSourceModel = dtofield.sourceModel();
			if (!"".equals(dtofield.field()))
				field = dtofield.field();
		}
		if (field == null) { // 如果@DtoField未定义field属性，默认就取属性名作为映射的model属性
			field = dtoPropDescriptor.getName();
		}
		if (fieldSourceModel == null) {
			fieldSourceModel = "";
		}
		Object model = srcModelMap.get(fieldSourceModel);
		try {
			Object fieldValue = null;
			PropertyDescriptor modelPropDscriptor;
			if ("/".equals(field)) {
				fieldValue = model;
			} else if (model != null
					&& (modelPropDscriptor = PropertyUtils.getPropertyDescriptor(model, field)) != null
					&& modelPropDscriptor.getReadMethod() != null) {
				fieldValue = PropertyUtils.getProperty(model, field);
			}
			if (List.class.isAssignableFrom(dtoPropDescriptor.getPropertyType())) {
				Type type = dtoPropDescriptor.getReadMethod().getGenericReturnType();
				fieldValue = createListDtoValue(buildListener, type, fieldValue);
			} else {
				fieldValue = createDtoValue(buildListener, dtoPropDescriptor.getPropertyType(), fieldValue);
			}
			if (fieldValue != null) {
				PropertyUtils.setProperty(dto, dtoPropDescriptor.getName(), fieldValue);
			} else {
				PropertyUtils.setProperty(dto, dtoPropDescriptor.getName(), null);
			}
		} catch (Exception ex) {
			if (!(ex instanceof NestedNullException)) { // We don't care nested
														// null object, just
														// skip this mapping.
				throw new DtoException("Failed to build DTO field:" + field, ex);
			}
		}
	}

	/**
	 * 根据model的值生成dto的值
	 * 
	 * @param buildListenerMap
	 * @param dtoType
	 * @param modelValue
	 * @return
	 */
	private Object createDtoValue(Map buildListenerMap, Type dtoType, Object modelValue) {
		if (modelValue == null)
			return null;
		if (dtoType instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) dtoType).getRawType();
			if ((rawType instanceof Class) && List.class.isAssignableFrom((Class) rawType)) {
				return createListDtoValue(buildListenerMap, dtoType, modelValue);
			}
		} else if ((dtoType instanceof Class) && isAnnotatedDto((Class) dtoType))
			modelValue = buildDto(buildListenerMap, (Class) dtoType, new Object[] { modelValue });
		return modelValue;
	}

	private List createListDtoValue(Map buildListenerMap, Type type, Object obj) {
		if (obj == null) {
			return null;
		}
		if (!(obj instanceof Collection)) {
			throw new DtoException("Unmatched Type");
		}
		Collection col = (Collection) obj;
		ArrayList arraylist = new ArrayList();
		if (type instanceof Class) {
			arraylist.addAll(col);
		} else if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			for (Object o : col) {
				arraylist.add(createDtoValue(buildListenerMap, type, o));
			}
		}
		return arraylist;
	}

	/**
	 * 构建sourceModelName到model的映射，如果DtoField未定义sourceModel，
	 * 则取model的第一个为sourceModel
	 * 
	 * @param dto
	 * @param models
	 * @return
	 */
	private HashMap createSourceObjectMap(Dto dto, Object models[]) {
		HashMap srcObjMap = new HashMap();
		for (int i = 0; i < models.length; i++) {
			String sourceModelName;
			if (dto.sourceModelName().length > i && !"".equals(dto.sourceModelName()[i])) {
				sourceModelName = dto.sourceModelName()[i];
			} else {
				sourceModelName = (new StringBuilder()).append("#").append(i).toString();
			}

			srcObjMap.put(sourceModelName, models[i]);
			if (i == 0) {
				srcObjMap.put("", models[i]); // 默认的model就是提供的第一个
			}
		}

		return srcObjMap;
	}

	/************************************** Merge Model ****************************************************/

	public void mergeModels(Map<Class<?>, MergeModelEventListener> map, Object obj, Object aobj[]) {
		String as[] = null;
		Dto dto;
		if ((dto = (Dto) obj.getClass().getAnnotation(Dto.class)) != null)
			as = dto.sourceModelName();
		for (int i = 0; i < aobj.length; i++) {
			if (aobj[i] == null)
				continue;
			String s;
			if (as != null && as.length > i)
				s = as[i];
			else
				s = getDefaultSrcModelName(i);
			mergeModel(map, obj, s, aobj[i]);
		}
	}

	public Object mergeModel(Map<Class<?>, MergeModelEventListener> map, Object dto, String sourceModelClassName,
			Object model) {
		return mergeModel(map, dto, model != null ? model.getClass() : null,
				sourceModelClassName != null ? sourceModelClassName : "", model);
	}

	@SuppressWarnings("unchecked")
	protected Object mergeModel(Map<Class<?>, MergeModelEventListener> map, Object dto, Type modelType,
			String sourceModelClassName, Object model) {

		// DTO为空时，直接返回model
		if (dto == null) {
			return model;
		}

		// DTO是List时，如果Model不是List则抛出异常，否则调用mergeModelList方法
		if (dto instanceof List) {
			if (model == null || (model instanceof List)) {
				return mergeModelList(map, (List) dto, modelType, (Collection) model);
			} else {
				throw new DtoException(new StringBuilder().append("Model is of type ").append(model.getClass())
						.append(" instead of Collection").toString());
			}
		}

		// 未标识@Dto的情况下，Dto可能为预设的简单类型之一，直接返回dto对象作为model
		if (!isAnnotatedDto(dto)) {
			return dto;
		}

		// Model是否为新建
		boolean isNewModel = false;

		Class modelClass = null;
		if (modelType != null && modelType instanceof Class) {
			modelClass = (Class) modelType;
		}

		Class dtoClass = dto.getClass();
		Dto dtoAnnotation = (Dto) dtoClass.getAnnotation(Dto.class);

		// 得到source model的name，以处理当model为null的情况下new一个model
		String firstSourceModelClassName = getDtoFirstSrcModelName(dtoAnnotation);
		if (sourceModelClassName == null || sourceModelClassName.length() == 0)
			sourceModelClassName = firstSourceModelClassName;

		// 如果未提供sourceModelClassName或者sourceModelClassName没有定义在Dto中，该值恒为0；否则为提供的sourceModelClassName在Dto定义中的下标
		int sourceModelIndex = getSrcModelIndex(dtoAnnotation, sourceModelClassName);

		if (model == null) { // 当没有提供Model的情况下，根据各种条件创建一个Model
			if (modelClass == null) {
				if (dtoAnnotation.sourceModelClass().length > sourceModelIndex)
					modelClass = dtoAnnotation.sourceModelClass()[sourceModelIndex];
				else
					throw new DtoException(new StringBuilder().append("Unknown model to instantiate for ")
							.append(dto.getClass()).toString());
			}
			try {
				model = modelClass.newInstance(); // 新建一个Model
			} catch (Exception ex) {
				throw new DtoException(new StringBuilder().append("Failed to instantiate ").append(modelClass)
						.toString(), ex);
			}
			isNewModel = true;
		} else if (dtoAnnotation.sourceModelClass().length > sourceModelIndex
				&& !dtoAnnotation.sourceModelClass()[sourceModelIndex].isAssignableFrom(model.getClass())) { // dto声明的sourcemodel与model的类型不等或者不为后者的父类/接口
			throw new DtoException(new StringBuilder().append("Input model is of type ").append(model.getClass())
					.append(", which is unmatched with required").append(dtoAnnotation.sourceModelClass()[0])
					.toString());
		}

		PropertyDescriptor propDescriptors[] = PropertyUtils.getPropertyDescriptors(dtoClass);
		int dtoPropLen = propDescriptors.length;
		for (int j = 0; j < dtoPropLen; j++) { // 对Dto类中的每个field进行逐一映射
			PropertyDescriptor dtoPropDescriptor = propDescriptors[j];
			boolean hasVersion = false;
			if (!isAnnotatedDto(getPropertyDeclaringClass(dtoPropDescriptor)) // 如果该属性的定义类非@Dto，或者该属性没有getter，则忽略不处理
					|| dtoPropDescriptor.getReadMethod() == null)
				continue;

			// 根据DtoField的定义进行转换
			DtoField dtofield = getDtoFieldAnnotation(dtoClass, dtoPropDescriptor);
			String modelClassName = firstSourceModelClassName;
			String dtoPropName = dtoPropDescriptor.getName();
			String modelPropName = dtoPropName;
			if (dtofield == null && dtoAnnotation.ignoreUnannoatedField())
				continue;
			if (dtofield != null) {
				if (dtofield.ignore()) {
					continue;
				}
				if (dtofield.version()) {
					hasVersion = true; // 标识dtofield有version
				} else if (dtofield.skipMerge()) {
					continue;
				}
				if (dtofield.sourceModel().length() > 0) { // 如果DtoField定义有sourceModel，则采用，否则，则采用Dto的首选定义
					modelClassName = dtofield.sourceModel();
				} else if (dtoAnnotation.sourceModelName().length > 0
						&& dtoAnnotation.sourceModelName()[0].length() > 0) {
					modelClassName = dtoAnnotation.sourceModelName()[0];
				}
				if (!"".equals(dtofield.field())) {
					modelPropName = dtofield.field();
				}
			}
			if (!sourceModelClassName.equals(modelClassName)) { // ?
				continue;
			}

			// 写进model
			try {
				PropertyDescriptor modelPropDescriptor = PropertyUtils.getPropertyDescriptor(model, modelPropName);
				if (modelPropDescriptor == null) {
					throw new DtoException(new StringBuilder().append("Property ").append(modelPropName)
							.append(" can not be found in class ").append(modelClass).toString());
				}
				if (modelPropDescriptor.getWriteMethod() == null) {
					continue;
				}
				Object dtoPropValue = PropertyUtils.getProperty(dto, dtoPropName);
				Object modelPropValue;
				if ("/".equals(modelPropName)) // 以"/"作为field名称的，就代表model本身
					modelPropValue = model;
				else
					modelPropValue = PropertyUtils.getProperty(model, modelPropName);

				// 如果该属性是List属性，则需要调用List的merge方法
				if (List.class.isAssignableFrom(modelPropDescriptor.getPropertyType())) {
					dtoPropValue = mergeModel(map, dtoPropValue, modelPropDescriptor.getPropertyType(), null, null);
				}

				if (!isNewModel && hasVersion) {
					if (dtoPropValue == null || modelPropValue == null) {
						logger.info("DTO / Destination Version field is null. Skip version checking");
						continue;
					}
					if (!dtoPropValue.equals(modelPropValue))
						throw new DtoException(new StringBuilder().append("DTO version: ").append(dtoPropDescriptor)
								.append(", Model version: ").append(modelPropValue).toString());
					continue;
				}
				// Object type1 = mergeModel(map, dtoPropDescriptor,
				// getPropertyGetterType(modelPropDescriptor), "",
				// modelPropValue);
				PropertyUtils.setProperty(model, modelPropName, dtoPropValue);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}

		// 触发MergeModelEventListener动作
		if (map.containsKey(dto.getClass()))
			((MergeModelEventListener) map.get(dto.getClass())).afterMergeModels(dto, new Object[] { model });
		return model;
	}

	/**
	 * 返回传入的property的getter返回类型
	 * 
	 * @param propertydescriptor
	 * @return
	 */
	private static Type getPropertyGetterType(PropertyDescriptor propertydescriptor) {
		Type retType = propertydescriptor.getReadMethod().getGenericReturnType();

		// 如果property类型是某种List，则需要剔除掉Hiberate等框架的lazy loading机制所使用的代理类，找到真正的定义类
		if (List.class.isAssignableFrom(propertydescriptor.getPropertyType())) {
			Class propDeclaringClazz;
			for (propDeclaringClazz = propertydescriptor.getReadMethod().getDeclaringClass(); HibernateProxy.class
					.isAssignableFrom(propDeclaringClazz) || Factory.class.isAssignableFrom(propDeclaringClazz); propDeclaringClazz = propDeclaringClazz
					.getSuperclass())
				;
			try {
				retType = propDeclaringClazz.getMethod(propertydescriptor.getReadMethod().getName(), new Class[0])
						.getGenericReturnType();
			} catch (Exception exception) {
				logger.info(new StringBuilder().append("failed to get read method ")
						.append(propertydescriptor.getReadMethod().getName()).append("from class ")
						.append(propDeclaringClazz).append(" ").append(exception).toString());
			}
		}
		return retType;
	}

	// 查找指定的className在Dto的sourceModelName中的下标，如未发现，则返回0
	private static int getSrcModelIndex(Dto dto, String sourceModelClassName) {
		if (sourceModelClassName == null || sourceModelClassName.length() == 0)
			return 0;
		if (dto != null) {
			for (int i = 0; i < dto.sourceModelName().length; i++)
				if (sourceModelClassName.equals(dto.sourceModelName()[i]))
					return i;

		}
		if (sourceModelClassName.startsWith("#"))
			return Integer.parseInt(sourceModelClassName.substring(1));
		else
			return 0;
	}

	public Collection mergeModelList(Map map, List dtoList, Type type, Collection modelCollection) {

		// Dto集合为空时，返回空的Model集合
		if (dtoList == null || dtoList.size() == 0) {
			if (modelCollection != null)
				modelCollection.clear();
			return modelCollection;
		}

		Type type1 = null;
		if (type instanceof ParameterizedType)
			type1 = ((ParameterizedType) type).getActualTypeArguments()[0];

		String idFields[] = null;
		Dto dto = null;
		Class clazz = null;

		// 获得目标Model内表示唯一ID的field集合
		Iterator iterator = dtoList.iterator();
		while (iterator.hasNext()) {
			Object dtoObj = iterator.next();
			if (dtoObj == null)
				continue;
			clazz = dtoObj.getClass();
			dto = (Dto) dtoObj.getClass().getAnnotation(Dto.class);
			if (dto != null && dto.idField().length > 0) {
				idFields = (String[]) dto.idField().clone();

				for (int i = 0; i < dto.idField().length; i++) {
					PropertyDescriptor pd;
					try {
						pd = PropertyUtils.getPropertyDescriptor(dtoObj, dto.idField()[i]);
					} catch (Exception _ex) {
						throw new DtoException(new StringBuilder().append("ID Field \"").append(dto.idField())
								.append("\"not found for class ").append(clazz).toString());
					}
					if (pd.getReadMethod() == null)
						throw new DtoException(new StringBuilder().append("ID Field \"")
								.append(((PropertyDescriptor) (pd)).getName()).append("\" not readable for class ")
								.append(clazz).toString());

					DtoField dtoField = getDtoFieldAnnotation(clazz, pd);
					if (dtoField != null && !"".equals(dtoField.field()))
						idFields[i] = dtoField.field();
				}

			}
		}

		if (modelCollection == null) {
			modelCollection = new ArrayList(dtoList.size());
		}
		if (idFields != null) { // 有ID的情况
			mergeIdAwareModelList(map, dtoList, type1, modelCollection, dto.idField(), idFields);
		} else {
			// 没有ID的情况下，直接对每一个dto进行merge
			modelCollection.clear();
			for (iterator = dtoList.iterator(); iterator.hasNext();) {
				Object dtoObj = iterator.next();
				modelCollection.add(mergeModel(map, dtoObj, type1, "", null));
			}
		}
		return modelCollection;
	}

	/**
	 * Merge有一组有ID标识的Dto到Model
	 * 
	 * @param map
	 * @param dtoList
	 * @param type
	 * @param modelCollection
	 * @param dtoIdFields
	 * @param modelIdFields
	 * @return
	 */
	private Collection mergeIdAwareModelList(Map map, List dtoList, Type type, Collection modelCollection,
			String dtoIdFields[], String modelIdFields[]) {
		Map givenIdModelMap = new HashMap(); // 提供的model集合内id与对象的map
		List newModelList = new LinkedList(); // 根据DTO的id新建的model对象集合

		// 根据给出的model集合创建一个model id映射到model的map
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			Object model = iterator.next();
			try {
				List modelIdList = new ArrayList(modelIdFields.length);
				for (int i = 0; i < modelIdFields.length; i++) {
					modelIdList.add(PropertyUtils.getProperty(model, modelIdFields[i]));
				}

				givenIdModelMap.put(modelIdList, model);
			} catch (IllegalAccessException _ex) {
			} catch (InvocationTargetException _ex) {
			} catch (NoSuchMethodException _ex) {
			}
		}

		// 根据给出的拥有ID定义的DTO对象集合找到对应的Model对象，加入modelList里，并从idModelMap中剔除这个Model
		// 在没找到的情况下生成一个新的Model，加入modelList里
		for (Iterator dtoIterator = dtoList.iterator(); dtoIterator.hasNext();) {
			Object dtoObj = dtoIterator.next();
			try {
				List dtoIdList = new ArrayList(dtoIdFields.length);
				for (int i = 0; i < dtoIdFields.length; i++) {
					dtoIdList.add(PropertyUtils.getProperty(dtoObj, dtoIdFields[i]));
				}

				Object rightModel = givenIdModelMap.get(dtoList); // that model
																	// which has
																	// same ids
																	// as in DTO
																	// class
				if (rightModel == null) {
					rightModel = mergeModel(map, dtoObj, type, "", null);
					newModelList.add(rightModel);
				} else {
					mergeModel(map, dtoObj, type, "", rightModel);
					givenIdModelMap.remove(dtoIdList);
				}
				modelCollection.add(rightModel);
			} catch (IllegalAccessException _ex) {
			} catch (InvocationTargetException _ex) {
			} catch (NoSuchMethodException _ex) {
			}
		}

		// 删除在ID上没有对应DTO的Model
		for (Iterator modelIterator = modelCollection.iterator(); modelIterator.hasNext();) {
			Object model = modelIterator.next();
			try {
				List idList = new ArrayList(modelIdFields.length);
				for (int i = 0; i < modelIdFields.length; i++) {
					idList.add(PropertyUtils.getProperty(model, modelIdFields[i]));
				}
				if (givenIdModelMap.containsKey(idList))
					modelIterator.remove();
			} catch (IllegalAccessException _ex) {
			} catch (InvocationTargetException _ex) {
			} catch (NoSuchMethodException _ex) {
			}
		}

		if (!isSameOrder(modelCollection, newModelList)) {
			modelCollection.clear();
			modelCollection.addAll(newModelList);
		}
		return modelCollection;
	}

	// 判断两个集合内的元素是否有着完全相同，并且保持相同的顺序，相同的集合长度
	private boolean isSameOrder(Collection collectiona, Collection collectionb) {
		Iterator ita = collectiona.iterator();
		Iterator itb = collectionb.iterator();

		for (; ita.hasNext() && itb.hasNext();)
			if (ita.next() != itb.next())
				return false;

		return !ita.hasNext() && !itb.hasNext();
	}

	/**
	 * 得到某个property的定义类
	 * 
	 * @param propertydescriptor
	 * @return
	 */
	private Class getPropertyDeclaringClass(PropertyDescriptor propertydescriptor) {
		Class clazz;
		if (propertydescriptor.getReadMethod() != null)
			clazz = propertydescriptor.getReadMethod().getDeclaringClass();
		else
			clazz = propertydescriptor.getWriteMethod().getDeclaringClass();
		return clazz;
	}

	/**
	 * 是否为加上@Dto的Dto对象
	 * 
	 * @param dto
	 * @return
	 */
	protected boolean isAnnotatedDto(Object dto) {
		if (dto == null)
			return false;
		else
			return isAnnotatedDto(dto.getClass());
	}

	/**
	 * 该Class是否有@Dto注解
	 * 
	 * @param dto
	 * @return
	 */
	protected boolean isAnnotatedDto(Class<?> clazz) {
		return getDtoAnnotation(clazz) != null;
	}

	/**
	 * 返回clazz的Dto注解
	 * 
	 * @param clazz
	 * @return
	 */
	protected Dto getDtoAnnotation(Class<?> clazz) {
		if (nonDtoClasses.contains(clazz)) {
			return null;
		}
		Dto dto;
		if ((dto = (Dto) clazz.getAnnotation(Dto.class)) == null) {
			nonDtoClasses.add(clazz);
		}
		return dto;
	}

	/**
	 * 得到Dto的第一个定义的sourceModelName，如果没有，返回默认的#0
	 * 
	 * @param dto
	 * @return
	 */
	protected String getDtoFirstSrcModelName(Dto dto) {
		if (dto.sourceModelName().length > 0)
			return dto.sourceModelName()[0];
		else
			return defaultSourceModelNames[0];
	}

	/**
	 * 得到一个property对应的DtoField声明
	 * 
	 * @param clazz
	 * @param propertydescriptor
	 * @return
	 */
	protected DtoField getDtoFieldAnnotation(Class clazz, PropertyDescriptor propertydescriptor) {
		DtoField dtofield;

		// 遍历找到该class的类继承结构中最底端的定义有@Dto的class，即如果clazz为@Dto，则就对应它
		for (dtofield = null; dtofield == null && clazz != Object.class; clazz = clazz.getSuperclass()) {
			if (!isAnnotatedDto(clazz))
				continue;
			try {
				Field field = clazz.getDeclaredField(propertydescriptor.getName());
				field.setAccessible(true);
				dtofield = (DtoField) field.getAnnotation(DtoField.class);
			} catch (SecurityException ex) {
			} catch (NoSuchFieldException ex) {
			}
		}

		// 如果field上面没有定义，那就在getter/setter上寻找
		if (dtofield == null && propertydescriptor.getReadMethod() != null)
			dtofield = (DtoField) propertydescriptor.getReadMethod().getAnnotation(DtoField.class);

		if (dtofield == null && propertydescriptor.getWriteMethod() != null)
			dtofield = (DtoField) propertydescriptor.getWriteMethod().getAnnotation(DtoField.class);
		return dtofield;
	}

	/**
	 * 获取默认sourceModelName
	 * 
	 * @param i
	 * @return
	 */
	protected String getDefaultSrcModelName(int i) {
		if (i >= defaultSourceModelNames.length)
			synchronized (this) {
				if (i >= defaultSourceModelNames.length) {
					String as[] = new String[i + 1];
					for (int j = 0; j < as.length; j++)
						as[j] = new StringBuilder().append("#").append(j).toString();

					defaultSourceModelNames = as;
				}
			}
		return defaultSourceModelNames[i];
	}
}