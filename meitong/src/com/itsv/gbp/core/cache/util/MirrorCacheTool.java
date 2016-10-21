package com.itsv.gbp.core.cache.util;

import java.util.Collections;
import java.util.List;

import net.sf.ehcache.CacheException;

import org.apache.commons.beanutils.PropertyUtils;
import org.josql.Query;
import org.josql.QueryResults;

import com.itsv.gbp.core.cache.IMirrorCache;
import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.MirrorCacheSerivce;

/**
 * ���񻺴湤���ࡣ<br>
 * ע�⣺����ר�������ṩ�Ծ��񻺴�ı�ݷ��ʡ������Ҫ����ͨ������ʣ�����ʹ�á���Ϊ���񻺴����һ��ǰ׺��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����03:33:38
 * @version 1.0
 */
public class MirrorCacheTool {

	/**
	 * ��ȡָ���Ļ�������
	 * 
	 * @param cacheRegion ����������
	 * @return
	 */
	public static IMirrorCache getRegion(String cacheRegion) {
		return MirrorCacheSerivce.getInstance().getCache(cacheRegion);
	}

	/**
	 * ˢ��ָ���Ļ�������
	 * 
	 * @param cacheRegion ����������
	 */
	public static void refresh(String cacheRegion) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache != null) {
			cache.refresh();
		}
	}

	/**
	 * �õ�ָ�������ָ��ֵ�Ļ������
	 * 
	 * @param cacheRegion
	 * @param key
	 * @return
	 */
	public static Object get(String cacheRegion, Object key) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache != null) {
			return cache.get(key);
		}
		return null;
	}

	/**
	 * �õ�ָ����������ĳ������ֵ��<br>
	 * ���ճ�Ӧ���У���õ��ǵõ���������ĳ�����ԣ���������������
	 * 
	 * @param cacheRegion
	 * @param key
	 * @param propertyName
	 * @return
	 */
	public static Object getProp(String cacheRegion, Object key, String propertyName) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache == null) {
			return null;
		}
		Object obj = cache.get(key);
		if (obj == null) {
			return null;
		}
		try {
			return PropertyUtils.getProperty(obj, propertyName);
		} catch (Exception e) {
			throw new MirrorCacheException("��ö����[" + propertyName + "]����ֵʱ����", e);
		}

	}

	/**
	 * �õ�ָ��������������л������
	 * 
	 * @param cacheRegion
	 * @return
	 */
	public static List getAll(String cacheRegion) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache != null) {
			return cache.getAll();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * �Ի������������ѯ��ʹ����<a href="http://josql.sf.net">JoSQL</a>�����<br>
	 * 
	 * ����Ϊ��TestVO����Ĳ�ѯ��䣺<br>
	 * <pre>
	 * select * 
	 * from com.itsv.gbp.core.cache.model.TestVO
	 * where name='key-2' or (code>=993 and name='key-3') 
	 * order by code desc
	 * </pre>
	 * 
	 * �����ѯ�﷨��<a href="http://josql.sf.net">JoSQL</a>
	 * 
	 * @param cacheRegion
	 * @param sql
	 * @return
	 */
	public static List query(String cacheRegion, String fullSql) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache == null) {
			return Collections.EMPTY_LIST;
		}

		Query q = new Query();
		try {
			q.parse(fullSql);
			QueryResults results = q.execute(cache.getAll());

			return results.getResults();
		} catch (Exception e) {
			throw new CacheException("�Ի���ִ�в�ѯʱ����sql=[" + fullSql + "]", e);
		}
	}

	/**
	 * �򻯵Ļ���������ѯ������ʡ��select��from��䡣<br>
	 * 
	 * ���������sql������£�
	 * <pre>
	 * select * 
	 * from com.itsv.gbp.core.cache.model.TestVO
	 * where name='key-2' or (code>=993 and name='key-3') 
	 * order by code desc
	 * </pre>
	 * 
	 * ��ô��simplyQuery���ü�Ϊ������ʽ��
	 * <pre>
	 * String where = "name='key-2' or (code>=993 and name='key-3')"; 
	 * String order = "code desc";
	 * simplyQuery(region, where, order);
	 * </pre>
	 * (��������ͻ��Cache����õ���)

	 * @param cacheRegion
	 * @param whereCondition where������ע�⣺ʡ��where
	 * @param orderCondtion orderby������ע�⣺ʡ��order by
	 * @return
	 */
	public static List simplyQuery(String cacheRegion, String whereCondition, String orderCondtion) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache == null) {
			return Collections.EMPTY_LIST;
		}

		String type = cache.getCachedObjectClass().getName();
		StringBuffer sql = new StringBuffer().append("select * from ").append(type);
		if (whereCondition != null && whereCondition.length() > 0) {
			sql.append(" where ").append(whereCondition);
		}
		if (orderCondtion != null && orderCondtion.length() > 0) {
			sql.append(" order by ").append(orderCondtion);
		}

		return query(cacheRegion, sql.toString());
	}

	/**
	 * ͬ�ϣ�����order by�����ļ򻯲�ѯ
	 * 
	 * @param cacheRegion
	 * @param whereCondition
	 * @return
	 */
	public static List simplyQuery(String cacheRegion, String whereCondition) {
		return simplyQuery(cacheRegion, whereCondition, null);
	}
}
