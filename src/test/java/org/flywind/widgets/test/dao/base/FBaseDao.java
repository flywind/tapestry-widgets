package org.flywind.widgets.test.dao.base;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.flywind.widgets.core.dao.FPage;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * <p>操作数据库的基础接口，实现对数据库的增、删、改、查</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public interface FBaseDao<T> {
	
	/**
	 * 获得当前事物的session
	 * 
	 * @return {@link Session}
	 */
	public Session getSession();
	
	/**
	 * 保存一个对象
	 * @param o
	 * 			对象
	 * @return
	 * 			对象的唯一标识(主键)
	 */
	public Long save(T o);
	
	/**
	 * 删除一个对象
	 * @param o
	 * 			对象
	 */
	public void delete(T o);
	
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
	public boolean deleteById(Class<T> c, Long id);
	
	/**
	 * 更新一个对象
	 * @param o
	 * 			对象
	 */
	public void update(T o);
	
	/**
	 * 保存或更新一个对象
	 * @param o
	 * 			对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 通过唯一标识(主键)查询出对应的对象
	 * 
	 * @param c
	 * 			对象所对应类的Class类型
	 * @param id
	 * 			唯一标识(主键)
	 * @return
	 * 			对象
	 */
	public T getById(Class<T> c, Long id);
	
	/**
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			对象
	 */
	public T getByHql(String hql);

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
	public T getByHql(String hql, Map<String, Object> params);
	
	/**
	 * 通过SQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			对象
	 */
	public T getBySql(String sql);
	
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
	public T getBySql(String sql, Map<String, Object> params);
	
	/**
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			对象集合
	 */
	public List<T> query(String hql);
	
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
	public List<T> query(String hql, Map<String, Object> params);
	
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
	public List<T> query(String hql, int page, int rows);
	
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
	public List<T> query(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			总记录(行)数
	 */
	public Long count(String hql);
	
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
	public Long count(String hql, Map<String, Object> params);
	
	/**
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL语句
	 * @return
	 * 			影响的记录(行)数
	 */
	public int executeHql(String hql);
	
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
	public int executeHql(String hql, Map<String, Object> params);
	
	/**
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			结果集
	 */
	public List<Map> queryBySql(String sql);
	
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
	public List<Map> queryBySql(String sql, Map<String, Object> params);
	
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
	public List<Map> queryBySql(String sql, int page, int rows);
	
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
	public List<Map> queryBySql(String sql, Map<String, Object> params, int page, int rows);
	
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
	 * @param pagingEntity
	 * 			分页对象
	 * @return
	 * 			对象集合
	 */
	public List<T> queryObjListBySql(String sql, Map<String, Object> params, 
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage pagingEntity);
	
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
	 * @param pagingEntity
	 * 			分页对象
	 * @return
	 * 			对象集合
	 */
	public List<T> queryObjListBySql(String sql, String countSql, Map<String, Object> params, 
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage pagingEntity);
	
	/**
	 * 执行存储过程
	 * 
	 * @param procedure
	 * 			过程语句
	 * @return
	 * 			对象
	 */
	public Object procedureCall(String procedure);
	
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
	public Object procedureCall(String procedure, Map<String, Object> params);
	
	/**
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			影响的记录(行)数
	 */
	public int executeSql(String sql);
	
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
	public int executeSql(String sql, Map<String, Object> params);
	
	/**
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL语句
	 * @return
	 * 			总记录(行)数
	 */
	public BigInteger countBySql(String sql);
	
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
	public BigInteger countBySql(String sql, Map<String, Object> params);
	
}
