package com.itsv.gbp.core.orm.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.itsv.gbp.core.cache.util.StringTool;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.ibatis.support.SqlMapDaoSupportPlus;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * ʹ��ibatis��Ϊ�����ϵӳ�����ʱ��dao���ࡣ<br>
 * 
 * ����ͳһʹ�øû��࣬������Ϊÿ�����󿪷�dao�ࡣ<br>
 * ���ڸ���ᱻ���service����ͬʱʹ�ã���Ӧע�Ⲣ���ԣ���Ҫʹ�ø�������Դ洢�м�״̬��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-4 ����11:09:07
 * @version 1.0
 */
public class IbatisBaseDao extends SqlMapDaoSupportPlus {

	/**
	 * ȱʡ�Ĳ�ѯ��������
	 */
	public static final String DEFAULT_QUERY_NAME = "query";

	/**
	 * ȱʡ�Ĳ�ѯ�ܼ�¼������������
	 */
	public static final String DEFAULT_QUERY_COUNT_NAME = "query_COUNT";

	/**
	 * ȱʡ�Ĳ�����������
	 */
	public static final String DEFAULT_INSERT_NAME = "insert";

	/**
	 * ȱʡ�ĸ�����������
	 */
	public static final String DEFAULT_UPDATE_NAME = "update";

	/**
	 * ȱʡ��ɾ����������
	 */
	public static final String DEFAULT_DELETE_NAME = "delete";

	/**
	 * ���캯��
	 */
	public IbatisBaseDao() {
		super();
	}

	/**
	 * ʹ��ȱʡ�������һ������
	 * 
	 * @param param ������������
	 * @param namespace ibatis�����ռ�
	 * @return
	 * @throws OrmException
	 */
	public Object add(Object param, String namespace) throws OrmException {
		return add(param, namespace, DEFAULT_INSERT_NAME);
	}

	/**
	 * ʹ��ָ��������Ӷ���
	 * 
	 * @param param ������������
	 * @param namespace ibatis�����ռ�
	 * @return
	 * @throws OrmException
	 */
	public Object add(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("ִ������, �������=" + StringTool.qualify(namespace, sqlName));
		}
		try {
			return getSqlMapClientTemplate().insert(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("ִ���������[" + StringTool.qualify(namespace, sqlName) + "]ʱ����", e);
		}

	}

	/**
	 * ʹ��ȱʡ�����¶���
	 * 
	 * @param param ������������
	 * @param namespace ibatis�����ռ�
	 * @return
	 * @throws OrmException
	 */
	public int update(Object param, String namespace) throws OrmException {
		return update(param, namespace, DEFAULT_UPDATE_NAME);
	}

	/**
	 * ʹ��ָ����������¶���
	 * 
	 * @param param ������������
	 * @param namespace ibatis�����ռ�
	 * @param sqlName Ҫִ�е������
	 * @return
	 * @throws OrmException
	 */
	public int update(Object param, String namespace, String sqlName) throws OrmException {
		if (logger.isDebugEnabled()) {
			logger.debug("ִ�и���, �������=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().update(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("ִ�и������[" + StringTool.qualify(namespace, sqlName) + "]ʱ����", e);
		}
	}

	/**
	 * ʹ��ȱʡɾ�����ɾ������
	 * 
	 * @param param	ɾ����������������
	 * @param namespace ibatis�����ռ�
	 * @return
	 * @throws OrmException
	 */
	public int remove(Object param, String namespace) throws OrmException {
		return remove(param, namespace, DEFAULT_DELETE_NAME);
	}

	/**
	 * ʹ��ָ�����ɾ������
	 * 
	 * @param param	ɾ������������
	 * @param namespace ibatis�����ռ�
	 * @param sqlName	
	 * @return
	 * @throws OrmException
	 */
	public int remove(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("ִ��ɾ��, �������=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().delete(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("ִ��ɾ�����[" + StringTool.qualify(namespace, sqlName) + "]ʱ����", e);
		}
	}

	public Object queryForObject(Object param, String namespace) throws OrmException {
		return queryForObject(param, namespace, DEFAULT_QUERY_NAME);
	}

	public Object queryForObject(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("ִ��query for object����,�������=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForObject(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("ִ�в�ѯ���[" + StringTool.qualify(namespace, sqlName) + "]ʱ����", e);
		}
	}

	/**
	 * ʹ��ȱʡ��ѯ�����з�ҳ��ѯ�����ط�ҳ����
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
			logger.debug("ִ�в�ѯ��ҳ�б�����,�������=" + StringTool.qualify(namespace, queryName));
		}

		//����ܼ�¼��Ϊ-1���򽫲�ѯ����������ҳ����
		if (records.getTotalNum() == -1) {
			records.setParam(param);
		}

		try {
			getSqlMapClientTemplate().query(StringTool.qualify(namespace, queryName),
					StringTool.qualify(namespace, queryCountName), records);
		} catch (DataAccessException e) {
			throw new OrmException("ִ�в�ѯ��ҳ�б�����[" + StringTool.qualify(namespace, queryName) + ","
					+ StringTool.qualify(namespace, queryCountName) + "]ʱ����", e);
		}
		return records;
	}

	public List queryForList(Object param, String namespace) throws OrmException {
		return queryList(param, namespace, DEFAULT_QUERY_NAME);
	}

	public List queryList(Object param, String namespace, String sqlName) throws OrmException {

		if (logger.isDebugEnabled()) {
			logger.debug("ִ��query for list����,�������=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForList(StringTool.qualify(namespace, sqlName), param);
		} catch (DataAccessException e) {
			throw new OrmException("ִ��query for list����[" + StringTool.qualify(namespace, sqlName) + "]ʱ����", e);
		}
	}

	public Map queryForMap(Object param, String namespace, String sqlName, String key) throws OrmException {
		if (logger.isDebugEnabled()) {
			logger.debug("ִ��query list����.key=" + key + ",�������=" + StringTool.qualify(namespace, sqlName));
		}

		try {
			return getSqlMapClientTemplate().queryForMap(StringTool.qualify(namespace, sqlName), param, key);
		} catch (DataAccessException e) {
			throw new OrmException("ִ��query list����[" + StringTool.qualify(namespace, sqlName) + "],key=["
					+ key + "]ʱ����", e);
		}
	}

}
