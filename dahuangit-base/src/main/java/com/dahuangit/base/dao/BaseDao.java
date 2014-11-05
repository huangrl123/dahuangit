package com.dahuangit.base.dao;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.dahuangit.base.entry.BaseModel;
import com.dahuangit.base.exception.GenericException;
import com.dahuangit.util.ReflectionUtils;

/**
 * 本项目基础DAO类，所有DAO都必须继承该类
 * <p>
 * 说明:在本类中，hibernate提供了新增、删除以及部分查询功能方法；
 * <p>
 * ibatis仅提供查询类方法，且这些方法名全以query开头。
 * <p>
 * 对于逻辑较复杂或者性能要求较高的查询，请使用query开头的ibatis方法.
 * 
 * @author 黄仁良
 * 
 *         创建时间2014年8月20日上午10:16:32
 */
public class BaseDao<T extends BaseModel, PK extends Serializable> {
	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	/** 当前的实体子类传递过来的具体类 */
	private Class<T> entityClass;

	@Resource(name = "sqlMapClientTemplate")
	private SqlMapClientTemplate sqlMapClientTemplate = null;

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate = null;

	public BaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 *            实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PK add(T entity) {
		return (PK) hibernateTemplate.save(entity);
	}

	/**
	 * 批量新增
	 * 
	 * @param entities
	 *            实体列表数据
	 * @return
	 */
	public void batchAdd(List<T> entities) {
		for (T entry : entities) {
			add(entry);
		}
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 *            需要删除的实体，主键属性必须赋值
	 */
	public void delete(T entity) {
		hibernateTemplate.delete(entity);
	}

	/**
	 * 批量删除
	 * 
	 * @param entities
	 *            需要删除的实体列表，主键属性必须赋值
	 */
	public void batchDelete(List<T> entities) {
		for (T entry : entities) {
			delete(entry);
		}
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体
	 * @return
	 */
	public void update(T entity) {
		hibernateTemplate.update(entity);
	}

	/**
	 * 批量修改
	 * 
	 * @param entities
	 *            实体列表
	 * @return
	 */
	public void batchUpdate(List<T> entities) {
		for (T entry : entities) {
			update(entry);
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param clazz
	 *            所查询的class类型
	 * @param pk
	 *            主键属性
	 * @return
	 */
	public T get(Class<T> clazz, PK pk) {
		return hibernateTemplate.get(clazz, pk);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 * 获取全部对象,支持排序.
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderBy));
		} else {
			c.addOrder(Order.desc(orderBy));
		}
		return c.list();
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values
	 * @return
	 */
	public List<T> findByPage(String hql, Integer start, Integer limit) {
		Validate.notNull(hql, "hql不能为空");
		Validate.notNull(start, "start不能为空");
		Validate.notNull(limit, "limit不能为空");

		Query query = this.createQuery(hql);

		if (0 != start && 0 != limit) {
			query.setFirstResult((start - 1) * limit);
			query.setMaxResults(limit);
		}

		List<T> list = query.list();

		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values
	 * @return
	 */
	public List<T> findByPage(String hql, Integer start, Integer limit, Object... values) {
		Validate.notNull(hql, "hql不能为空");
		Validate.notNull(start, "start不能为空");
		Validate.notNull(limit, "limit不能为空");
		Validate.notNull(values, "values不能为空");

		Query query = this.createQuery(hql, values);

		if (0 != start && 0 != limit) {
			query.setFirstResult((start - 1) * limit);
			query.setMaxResults(limit);
		}

		List<T> list = query.list();

		return null;
	}

	/**
	 * 获取记录总数(分页查询时使用)
	 * 
	 * @param hql
	 * @return
	 */
	public Integer findRecordsCount(String hql) {
		return (Integer) createQuery(hql).uniqueResult();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");

		Criterion criterion = Restrictions.eq(propertyName, value);

		if (null == createCriteria(criterion)) {
			return null;
		}

		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按id列表获取对象.
	 */
	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return this.hibernateTemplate.getSessionFactory();
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * 查询所有
	 * <p>
	 * 使用说明：需将resultMap 配置为相应的T类型
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @return 实体列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryForEntryList(String statementId) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId));
	}

	/**
	 * 查询所有
	 * <p>
	 * 使用说明：需将resultMap 配置为相应的T类型
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @param parameterObject
	 *            参数对象
	 * @return 实体列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryForEntryList(String statementId, Object parameterObject) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 查询所有,支持分页
	 * <p>
	 * 使用说明：需将resultMap 配置为相应的T类型
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @param parameterObject
	 *            参数对象
	 * @param start
	 *            当页起始记录下标数
	 * @param limit
	 *            每页记录数量
	 * @return 实体列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryForEntryList(String statementId, Object parameterObject, int start, int limit) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId), parameterObject, start, limit);
	}

	/**
	 * 通过对象传值，查询所有,返回map 列表
	 * <p>
	 * 使用说明：需将resultMap 配置为java.util.HashMap类型
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @return map列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForMapList(String statementId) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId));
	}

	/**
	 * 通过对象传值，查询所有,返回map 列表
	 * <p>
	 * 使用说明：需将resultMap 配置为java.util.HashMap类型
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @param parameterObject
	 *            参数对象
	 * @return map列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForMapList(String statementId, Object parameterObject) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 通过对象传值，查询所有,支持分页
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @param parameterObject
	 *            参数对象
	 * @param start
	 *            当页起始记录下标数
	 * @param limit
	 *            每页记录数量
	 * @return map列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForMapList(String statementId, Object parameterObject, int start, int limit) {
		return sqlMapClientTemplate.queryForList(fullStmtName(statementId), parameterObject, start, limit);
	}

	/**
	 * 单个查询，返回对象
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @return 对象
	 */
	public Object queryForObject(String statementId) {
		return sqlMapClientTemplate.queryForObject(fullStmtName(statementId));
	}

	/**
	 * 通过对象传值,单个查询，返回对象
	 * 
	 * @param statementId
	 *            sqlmap xml里的statement ID
	 * @param parameterObject
	 *            参数对象
	 * @return 对象
	 */
	public Object queryForObject(String statementId, Object parameterObject) {
		return sqlMapClientTemplate.queryForObject(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 获取sqlmap命名空间
	 * 
	 * @return
	 */
	public String getSqlMapNamespace() {
		return this.entityClass.getName();
	}

	/**
	 * 返回iBATIS statement的全名
	 * 
	 * @param statementId
	 * @return
	 */
	private String fullStmtName(String statementId) {
		return getSqlMapNamespace() + "." + statementId;
	}

	/**
	 * 执行sql方法,支持批量sql
	 * 
	 * @param sqlStr
	 */
	public void executeSqlStr(String sqlStr) {
		jdbcTemplate.execute(sqlStr);
	}

	/**
	 * 执行sql文件
	 * 
	 * @param filePath
	 */
	public void executeSqlFile(String filePath) {
		try {
			String sqlStr = FileUtils.readFileToString(new File(filePath), "UTF-8");
			this.executeSqlStr(sqlStr);
		} catch (Exception e) {
			throw new GenericException("执行sql文件出错", e);
		}
	}
}
