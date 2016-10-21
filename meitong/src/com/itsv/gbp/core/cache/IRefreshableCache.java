package com.itsv.gbp.core.cache;

import com.itsv.gbp.core.cache.source.ICacheDataSource;

/**
 * �Զ����µĻ���ӿڡ�<br>
 * Ҫ�󻺴�������һ������������Դ�������ڻ���ʧЧʱ�Զ����»�ȡ���ݡ�<br>
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����02:06:06
 * @version 1.0
 */
public interface IRefreshableCache {

	/**
	 * ˢ�»��汾����
	 */
	public void refresh();

	public RefreshableCacheState getState();

	public void setState(RefreshableCacheState state);

	public ICacheDataSource getDataSource();

	public void setDataSource(ICacheDataSource source);
}
