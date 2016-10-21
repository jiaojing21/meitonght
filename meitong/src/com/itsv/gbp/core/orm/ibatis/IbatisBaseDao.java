package com.itsv.gbp.core.orm.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.itsv.gbp.core.cache.util.StringTool;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.ibatis.support.SqlMapDaoSupportPlus;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * 使用ibatis作为对象关系映射组件时的dao基类。<br>
 * 
 * 建议统一使用该基类，而不再为每个对象开发dao类。<br>
 * 由于该类会被多个service对象同时使用，故应注意并发性，不要使用该类的属性存储中间状态。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-4 上午11:09:07
 * @version 1.0
 */
public class IbatisBaseDao extends SqlMapDaoSupportPlus {

	/**
	 * 缺省的查询语句的名称
	 */
	public static final String DEFAULT_QUERY_NAME = "query";

	/**
	 * 缺省的查询总记录个数语句的名称
	 */
	public static final String DEFAULT_QUERY_COUNT_NAME = "query_COUNT";

	/**
	 * 缺省的插入语句的名称
	 */
	public static final String DEFAULT_INSERT_NAME = "insert";

	/**
	 * 缺省的更新语句的名称
	 */
	public static final String DEFAULT_UPDATE_NAME = "update";

	/**
	 * 缺省的删除语句的名称
	 */
	public static final String DEFAULT_DELETE_NAME = "delete";

	/**
	 * 构造函数
	 */
	public IbatisBaseDao() {
		super();
	}

	/**
	 * 使用缺省语句增加一个对象
	 * 
	 * @param param 条件参数对象
	 * @param namespace ibatis命名空间
	 * @return
	 * @throws OrmException
	 */
	public Object add(Object param, String namespace) throws OrmException {
		return add(param, namespace, DEFAULT_INSERT_NAME);
	}

	/**
	 * 使用指定语句增加对象
	 * 
	 * @param param 条件参数对象
	 * @param namespace ibatis命名空间
	 * @return
	 * @throws OrmException
	 */
	public Object add(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("执行增加, 语句名称=" + StringTool.qualify(namespace, sqlName));
		}
		try {
			return getSqlMapClientTemplate().insert(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("执行增加语句[" + StringTool.qualify(namespace, sqlName) + "]时出错", e);
		}

	}

	/**
	 * 使用缺省语句更新对象
	 * 
	 * @param param 条件参数对象
	 * @param namespace ibatis命名空间
	 * @return
	 * @throws OrmException
	 */
	public int update(Object param, String namespace) throws OrmException {
		return update(param, namespace, DEFAULT_UPDATE_NAME);
	}

	/**
	 * 使用指定语句名更新对象
	 * 
	 * @param param 条件参数对象
	 * @param namespace ibatis命名空间
	 * @param sqlName 要执行的语句名
	 * @return
	 * @throws OrmException
	 */
	public int update(Object param, String namespace, String sqlName) throws OrmException {
		if (logger.isDebugEnabled()) {
			logger.debug("执行更新, 语句名称=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().update(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("执行更新语句[" + StringTool.qualify(namespace, sqlName) + "]时出错", e);
		}
	}

	/**
	 * 使用缺省删除语句删除对象
	 * 
	 * @param param	删除的条件参数对象
	 * @param namespace ibatis命名空间
	 * @return
	 * @throws OrmException
	 */
	public int remove(Object param, String namespace) throws OrmException {
		return remove(param, namespace, DEFAULT_DELETE_NAME);
	}

	/**
	 * 使用指定语句删除对象
	 * 
	 * @param param	删除的条件参数
	 * @param namespace ibatis命名空间
	 * @param sqlName	
	 * @return
	 * @throws OrmException
	 */
	public int remove(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("执行删除, 语句名称=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().delete(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("执行删除语句[" + StringTool.qualify(namespace, sqlName) + "]时出错", e);
		}
	}

	public Object queryForObject(Object param, String namespace) throws OrmException {
		return queryForObject(param, namespace, DEFAULT_QUERY_NAME);
	}

	public Object queryForObject(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("执行query for object请求,语句名称=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForObject(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("执行查询语句[" + StringTool.qualify(namespace, sqlName) + "]时出错", e);
		}
	}

	/**
	 * 使用缺省查询名进行分页查询，返回分页对象
	 * 
	 * @param param
	 * @param records
	 * @param namespace
	 * @return
	 * @throws OrmException
	 */
	public IPagedList queryForPagedList(Object param, IPagedList records, String namespace)
			throws OrmException {
		return queryForPagedList(param, records, namespace, DEFAULT_QUERY_NAME, DEFAULT_QUERY_COUNT_NAME);
	}

	public IPagedList queryForPagedList(Object param, IPagedList records, String namespace, String queryName,
			String queryCountName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("执行查询分页列表请求,语句名称=" + StringTool.qualify(namespace, queryName));
		}

		//如果总记录数为-1，则将查询参数赋给分页对象
		if (records.getTotalNum() == -1) {
			records.setParam(param);
		}

		try {
			getSqlMapClientTemplate().query(StringTool.qualify(namespace, queryName),
					StringTool.qualify(namespace, queryCountName), records);
		} catch (DataAccessException e) {
			throw new OrmException("执行查询分页列表请求[" + StringTool.qualify(namespace, queryName) + ","
					+ StringTool.qualify(namespace, queryCountName) + "]时出错", e);
		}
		return records;
	}

	public List queryForList(Object param, String namespace) throws OrmException {
		return queryList(param, namespace, DEFAULT_QUERY_NAME);
	}

	public List queryList(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("执行query for list请求,语句名称=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForList(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("执行query for list请求[" + StringTool.qualify(namespace, sqlName) + "]时出错", e);
		}
	}

	public Map queryForMap(Object param, String namespace, String sqlName, String key) throws OrmException {
		if (logger.isDebugEnabled()) {
			logger.debug("执行query list请求.key=" + key + ",语句名称=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForMap(StringTool.qualify(namespace, sqlName), param, key);
		} catch (DataAccessException e) {
			throw new OrmException("执行query list请求[" + StringTool.qualify(namespace, sqlName) + "],key=["
					+ key + "]时出错", e);
		}
	}

}
