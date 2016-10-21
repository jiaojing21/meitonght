package com.itsv.gbp.core.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.cache.IMirrorCache;
import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.RefreshableCacheState;
import com.itsv.gbp.core.cache.source.ICacheDataSource;

/**
 * 使用EHCache作为底层存储类实现的镜像缓存。
 * @see IMirrorCache
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-15 上午11:41:52
 * @version 1.0
 */
public class EHCacheImpl implements IMirrorCache {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EHCacheImpl.class);

	private Cache cache;

	private ICacheDataSource datasource;

	private Class cachedObjectClass;

	private RefreshableCacheState state = RefreshableCacheState.useless;

	public EHCacheImpl(Cache cache) {
		this.cache = cache;
	}

	public EHCacheImpl(Cache cache, ICacheDataSource datasource) {
		this.cache = cache;
		this.setDataSource(datasource);
	}

	public Object get(Object key) {
		checkState();
		Element element = this.cache.get(key);
		if (element != null) {
			return element.getObjectValue();
		}
		return null;
	}

	/**
	 * 待改进：不用每次都这么取
	 */
	public List getAll() {
		checkState();
		if (cache.getSize() == 0) {
			return Collections.EMPTY_LIST;
		}
		List values = new ArrayList();
		for (Iterator iter = cache.getKeys().iterator(); iter.hasNext();) {
			values.add(get(iter.next()));
		}
		return values;
	}

	public String getName() {
		return this.cache.getName();
	}

	public void put(Object key, Object value) {
		checkState();
		this.cache.put(new Element(key, value));
	}

	public void remove(Object key) {
		checkState();
		this.cache.remove(key);
	}

	public void removeAll() {
		checkState();
		this.cache.removeAll();
	}

	private void checkState() throws MirrorCacheException {
		if (this.getState() != RefreshableCacheState.normal) {
			throw new MirrorCacheException("缓存[" + this.getName() + "]的状态为[" + this.getState() + "],无法提供服务。");
		}
	}

	public void refresh() {
		if (logger.isDebugEnabled()) {
			logger.debug("开始刷新缓存[" + getName() + "]");
		}

		this.removeAll();

		Map map = this.getDataSource().getKeyValues();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			this.put(key, map.get(key));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("缓存[" + getName() + "]刷新结束，从数据源获取了 " + map.size() + " 条数据，该区域目前存储 "
					+ this.cache.getSize() + " 个缓存对象");
		}
	}

	public Class getCachedObjectClass() {
		if (this.cachedObjectClass != null) {
			return this.cachedObjectClass;
		} else {
			return getDataSource().getObjectType();
		}
	}

	public void setCachedObjectClass(Class cachedObjectClass) {
		this.cachedObjectClass = cachedObjectClass;
	}

	public ICacheDataSource getDataSource() {
		return this.datasource;
	}

	/**
	 * 只有正确设置了缓存数据源，该缓存才可用
	 */
	public void setDataSource(ICacheDataSource source) {
		this.datasource = source;
		source.setCache(this);

		this.state = RefreshableCacheState.normal;

		//设置数据源后，立即调用刷新方法，填充数据
		this.refresh();
	}

	public synchronized RefreshableCacheState getState() {
		return this.state;
	}

	public synchronized void setState(RefreshableCacheState state) {
		this.state = state;
	}

}
