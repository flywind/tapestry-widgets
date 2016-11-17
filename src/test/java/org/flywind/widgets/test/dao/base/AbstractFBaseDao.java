package org.flywind.widgets.test.dao.base;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.common.constants.FBaseConstants;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * <p>操作数据库的基类，实现对数据库的增、删、改、查</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public abstract class AbstractFBaseDao<T> implements FBaseDao<T> {
	
	/**
	 * session工厂
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 获得当前事物的session
	 * 
	 * @return {@link Session}
	 */
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存一个对象
	 * @param o
	 * 			对象
	 * @return
	 * 			对象的唯一标识(主键)
	 */
	@Override
	public Long save(T o) {
		if (null != o) {
			Serializable s = getSession().save(o);
			return null != s ? Long.parseLong(s.toString()) : null;
		} else {
			return null;
		}
	}

	/**
	 * 删除一个对象
	 * @param o
	 * 			对象
	 */
	@Override
	public void delete(T o) {
		if (null != o) {
			getSession().delete(o);
		}
	}
	
	/**
	 * 根据id删除对象
	 * 
	 * @param c
	 * 			对象的Class类型
	 * @param id
	 * 			对象id
	 * @return
	 * 			true删除成功，false删除失败
	 */
	@Override
	public boolean deleteById(Class<T> c, Long id) {
		if (null != c) {
			String className = c.getName();
			String hql = "DELETE FROM " + className + " o WHERE o.id = :id";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FBaseConstants.ID_STRING, id);
			int value = executeHql(hql, params);
			return value > 0 ? true : false;
		}
		return false;
	}

	/**
	 * 更新一个对象
	 * @param o
	 * 			对象
	 */
	@Override
	public void update(T o) {
		if (null != o) {
			getSession().update(o);
		}
	}

	/**
	 * 保存或更新一个对象
	 * @param o
	 * 			对象
	 */
	@Override
	public void saveOrUpdate(T o) {
		if (null != o) {
			getSession().saveOrUpdate(o);
		}
	}

	/**
	 * 通过唯一标识(主键)查询出对应的对象
	 * 
	 * @param c
	 * 			对象所对应类的Class类型
	 * @param id
	 * 			唯一标识(主键)
	 * @return
	 */
	@Override
	public T getById(Class<T> c, Long id) {
		return (T) getSession().get(c, id);
	}

	/**
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			对象
	 */
	@Override
	public T getByHql(String hql) {
		Query q = getSession().createQuery(hql);
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

	/**
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @return
	 * 			对象
	 */
	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}
	
	/**
	 * 通过SQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			对象
	 */
	@Override
	public T getBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

	/**
	 * 通过SQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @return
	 * 			对象
	 */
	@Override
	public T getBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

	/**
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> query(String hql) {
		Query q = getSession().createQuery(hql);
		
		return q.list();
	}

	/**
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		return q.list();
	}

	/**
	 * 通过HQL语句，以分页的方式查询出对象列表
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param page
	 * 			显示第几页数
	 * @param rows
	 * 			一页显示的记录(行)数
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> query(String hql, int page, int rows) {
		Query q = getSession().createQuery(hql);
		if (page > 0) {
			q.setFirstResult((page - 1) * rows);
		}
		if (rows > 0) {
			q.setMaxResults(rows);
		}
		
		return q.list();
	}

	/**
	 * 通过HQL语句，以分页的方式查询出对象列表
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @param page
	 * 			显示第几页数
	 * @param rows
	 * 			一页显示的记录(行)数
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (page > 0) {
			q.setFirstResult((page - 1) * rows);
		}
		if (rows > 0) {
			q.setMaxResults(rows);
		}
		
		return q.list();
	}

	/**
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			总记录(行)数
	 */
	@Override
	public Long count(String hql) {
		Query q = getSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	/**
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param params
	 * 			统计语句中需要的参数集合
	 * @return
	 * 			总记录(行)数
	 */
	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	/**
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			影响的记录(行)数
	 */
	@Override
	public int executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	/**
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL语句
	 * @param params
	 * 			HQL语句中需要的参数集合
	 * @return
	 * 			影响的记录(行)数
	 */
	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	/**
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			结果集
	 */
	@Override
	public List<Map> queryBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return q.list();
	}

	/**
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @return
	 * 			结果集
	 */
	@Override
	public List<Map> queryBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params & !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return q.list();
	}

	/**
	 * 通过SQL语句，以分页的方式查询出对象列表
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param page
	 * 			显示第几页数
	 * @param rows
	 * 			一页显示的记录(行)数
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<Map> queryBySql(String sql, int page, int rows) {
		SQLQuery q = getSession().createSQLQuery(sql);
		q.setFirstResult((page - 1) * rows);
		q.setMaxResults(rows);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return q.list();
	}

	/**
	 * 通过SQL语句，以分页的方式查询出对象列表
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @param page
	 * 			显示第几页数
	 * @param rows
	 * 			一页显示的记录(行)数
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<Map> queryBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		q.setFirstResult((page - 1) * rows);
		q.setMaxResults(rows);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return q.list();
	}

	/**
	 * 查询出对象列表
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @param resultValueTypeMap
	 * 			结果对象的属性与属性类型集合
	 * @param entityClass
	 * 			结果对象的Class类型
	 * @param page
	 * 			分页对象
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> queryObjListBySql(String sql, Map<String, Object> params, Map<String, Type> resultValueTypeMap,
			Class<T> entityClass, FPage page) {
		SQLQuery q = getSession().createSQLQuery(sql);
		
		//设置查询参数
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		//设置结果对象字段及字段类型
		if (null != resultValueTypeMap && !resultValueTypeMap.isEmpty()) {
			for (String key : resultValueTypeMap.keySet()) {
				q.addScalar(key, resultValueTypeMap.get(key));
			}
		}
		
		//设置分页
		if (null != page) {
			q.setFirstResult((page.getPageNumber() - 1) * page.getPageSize());
			q.setMaxResults(page.getPageSize());
		}
		
		AliasToBeanResultTransformer transformer = new AliasToBeanResultTransformer(entityClass);
		q.setResultTransformer(transformer);
		
		return q.list();
	}

	/**
	 * 查询出对象列表
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param countSql
	 * 			记录(行)数统计的SQL语句
	 * @param params
	 * 			查询语句中需要的参数集合
	 * @param resultValueTypeMap
	 * 			结果对象的属性与属性类型集合
	 * @param entityClass
	 * 			结果对象的Class类型
	 * @param page
	 * 			分页对象
	 * @return
	 * 			对象集合
	 */
	@Override
	public List<T> queryObjListBySql(String sql, String countSql, Map<String, Object> params,
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage page) {
		SQLQuery q = getSession().createSQLQuery(sql);
		
		//设置查询参数
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		//设置结果对象字段及字段类型
		if (null != resultValueTypeMap && !resultValueTypeMap.isEmpty()) {
			for (String key : resultValueTypeMap.keySet()) {
				q.addScalar(key, resultValueTypeMap.get(key));
			}
		}
		
		//设置分页
		if (null != page) {
			int rowCount = countBySql(countSql, params).intValue();
			page.setRowCount(rowCount);
			page.setDefaultValue();
			q.setFirstResult((page.getPageNumber() - 1) * page.getPageSize());
			q.setMaxResults(page.getPageSize());
		}

		AliasToBeanResultTransformer transformer = new AliasToBeanResultTransformer(entityClass);
		q.setResultTransformer(transformer);
		
		return q.list();
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedure
	 * 			过程语句
	 * @return
	 * 			对象
	 */
	@Override
	public Object procedureCall(String procedure) {
		
		//TODO 待实现
		
		return null;
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedure
	 * 			过程语句
	 * @param params
	 * 			过程语句中需要的参数集合
	 * @return
	 * 			对象
	 */
	@Override
	public Object procedureCall(String procedure, Map<String, Object> params) {
		
		//TODO 待实现
		
		return null;
	}

	/**
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			影响的记录(行)数
	 */
	@Override
	public int executeSql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	/**
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			SQL语句中需要的参数集合
	 * @return
	 * 			影响的记录(行)数
	 */
	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	/**
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			总记录(行)数
	 */
	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	/**
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL语句
	 * @param params
	 * 			统计语句中需要的参数集合
	 * @return
	 * 			总记录(行)数
	 */
	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}
	
}
