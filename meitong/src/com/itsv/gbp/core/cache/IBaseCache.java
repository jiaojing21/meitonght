package com.itsv.gbp.core.cache;

import java.util.List;

/**
 * 基本的缓存接口。主要增加了如下两个方法：<br>
 * getAll()	- 得到所有缓存对象
 * getCachedObjectClass()	- 得到缓存区域中存储对象的类型
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 上午10:38:08
 * @version 1.0
 */
public interface IBaseCache {

	/**
	 * 得到缓存的内部名称
	 * @return
	 */
	public String getName();

	/**
	 * 为缓存区域增加缓存对象
	 * 
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value);

	/**
	 * 得到指定key对应的缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key);

	/**
	 * 移除指定的缓存对象
	 * 
	 * @param key
	 */
	public void remove(Object key);

	/**
	 * 移除当前缓存区域的所有对象
	 *
	 */
	public void removeAll();

	/**
	 * 得到当前缓存区域缓存的所有对象
	 * 
	 * @return
	 */
	public List getAll();

	/**
	 * 得到缓存区域中存储对象的类型
	 * 
	 * @return
	 */
	public Class getCachedObjectClass();

}
