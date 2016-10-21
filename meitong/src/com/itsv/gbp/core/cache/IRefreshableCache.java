package com.itsv.gbp.core.cache;

import com.itsv.gbp.core.cache.source.ICacheDataSource;

/**
 * 自动更新的缓存接口。<br>
 * 要求缓存对象持有一个缓存数据来源，用来在缓存失效时自动重新获取数据。<br>
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午02:06:06
 * @version 1.0
 */
public interface IRefreshableCache {

	/**
	 * 刷新缓存本区域
	 */
	public void refresh();

	public RefreshableCacheState getState();

	public void setState(RefreshableCacheState state);

	public ICacheDataSource getDataSource();

	public void setDataSource(ICacheDataSource source);
}
