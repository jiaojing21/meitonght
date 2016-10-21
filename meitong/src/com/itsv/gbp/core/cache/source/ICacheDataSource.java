package com.itsv.gbp.core.cache.source;

import java.util.Map;

import com.itsv.gbp.core.cache.IMirrorCache;

/**
 * 缓存数据源。为了给缓存提供数据
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午12:00:13
 * @version 1.0
 */
public interface ICacheDataSource {

	/**
	 * 得到待缓存数据的key-value列表
	 * @return
	 */
	public Map getKeyValues();

	/**
	 * 得到待缓存对象的类型
	 * @return
	 */
	public Class getObjectType();

	/**
	 * 供缓存将自身反转提供给缓存数据源
	 * 
	 * @param cache
	 */
	public void setCache(IMirrorCache cache);

}
