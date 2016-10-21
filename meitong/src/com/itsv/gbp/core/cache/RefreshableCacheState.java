package com.itsv.gbp.core.cache;

/**
 * 缓存区域的状态。分别是：
 * 正常，
 * 脏缓存，等待刷新
 * 刷新中，
 * 不可用（正在启动或正在关闭）
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 上午10:44:41
 * @version 1.0
 */
public enum RefreshableCacheState {
	normal, dirty, flushing, useless
}
