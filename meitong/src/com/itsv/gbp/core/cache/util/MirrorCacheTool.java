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
 * 镜像缓存工具类。<br>
 * 注意：该类专门用来提供对镜像缓存的便捷访问。如果需要对普通缓存访问，不能使用。因为镜像缓存会有一个前缀。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午03:33:38
 * @version 1.0
 */
public class MirrorCacheTool {

	/**
	 * 获取指定的缓存区域
	 * 
	 * @param cacheRegion 缓存区域名
	 * @return
	 */
	public static IMirrorCache getRegion(String cacheRegion) {
		return MirrorCacheSerivce.getInstance().getCache(cacheRegion);
	}

	/**
	 * 刷新指定的缓存区域
	 * 
	 * @param cacheRegion 缓存区域名
	 */
	public static void refresh(String cacheRegion) {
		IMirrorCache cache = getRegion(cacheRegion);
		if (cache != null) {
			cache.refresh();
		}
	}

	/**
	 * 得到指定区域和指定值的缓存对象
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
	 * 得到指定缓存对象的某个属性值。<br>
	 * 在日常应用中，最常用的是得到缓存对象的某个属性，而不是整个对象。
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
			throw new MirrorCacheException("获得对象的[" + propertyName + "]属性值时出错", e);
		}

	}

	/**
	 * 得到指定缓存区域的所有缓存对象
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
	 * 对缓存进行条件查询。使用了<a href="http://josql.sf.net">JoSQL</a>组件。<br>
	 * 
	 * 如下为对TestVO对象的查询语句：<br>
	 * <pre>
	 * select * 
	 * from com.itsv.gbp.core.cache.model.TestVO
	 * where name='key-2' or (code>=993 and name='key-3') 
	 * order by code desc
	 * </pre>
	 * 
	 * 具体查询语法见<a href="http://josql.sf.net">JoSQL</a>
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
			throw new CacheException("对缓存执行查询时出错。sql=[" + fullSql + "]", e);
		}
	}

	/**
	 * 简化的缓存条件查询函数。省略select和from语句。<br>
	 * 
	 * 如果完整的sql语句如下：
	 * <pre>
	 * select * 
	 * from com.itsv.gbp.core.cache.model.TestVO
	 * where name='key-2' or (code>=993 and name='key-3') 
	 * order by code desc
	 * </pre>
	 * 
	 * 那么用simplyQuery可用简化为如下形式：
	 * <pre>
	 * String where = "name='key-2' or (code>=993 and name='key-3')"; 
	 * String order = "code desc";
	 * simplyQuery(region, where, order);
	 * </pre>
	 * (对象的类型会从Cache类里得到。)

	 * @param cacheRegion
	 * @param whereCondition where条件，注意：省略where
	 * @param orderCondtion orderby条件，注意：省略order by
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
	 * 同上，不带order by条件的简化查询
	 * 
	 * @param cacheRegion
	 * @param whereCondition
	 * @return
	 */
	public static List simplyQuery(String cacheRegion, String whereCondition) {
		return simplyQuery(cacheRegion, whereCondition, null);
	}
}
