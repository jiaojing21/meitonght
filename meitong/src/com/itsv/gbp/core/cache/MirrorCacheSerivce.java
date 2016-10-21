package com.itsv.gbp.core.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.itsv.gbp.core.cache.impl.EHCacheImpl;
import com.itsv.gbp.core.cache.source.ICacheDataSource;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * 镜像缓存的对外服务类。缓存的使用者只需要和这个类打交道。<br>
 * 该缓存对EHCache主要有两点增强：<br>
 * <ol>
 * <li>可对缓存区域进行检索，按照条件查询。</li>
 * <li>缓存可刷新，需要时自动刷新缓存，获取数据。</li>
 * </ol>
 * 该类设计为spring环境下的单态：既可以通过spring配置启动，又在程序访问中提供单态访问方式。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 上午10:57:26
 * @version 1.0
 */
public class MirrorCacheSerivce implements InitializingBean {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MirrorCacheSerivce.class);

    // 缺省的缓存前缀
    public static String DEFAULT_CACHE_PREFIX = "searchableCache";

    // 单态实例
    private static MirrorCacheSerivce instance = null;

    // 底层cache提供者，目前绑定EHCache
    private CacheManager cacheManager;

    // 缓存的前缀。为了与底层其他缓存区域区别开
    private String cachePrefix = DEFAULT_CACHE_PREFIX;

    // 存储所有可检索缓存
    private final Map<String, IMirrorCache> caches = new HashMap<String, IMirrorCache>();

    private Map<String, ICacheDataSource> initCache;

    /**
     * 单态方法，供程序应用中获取该类的单态实例
     * 
     * @return
     */
    public static MirrorCacheSerivce getInstance() {
        if (instance == null) {
            throw new MirrorCacheException("可检索缓存模块无法启动，请首先配置！");
        }
        return instance;
    }

    /**
     * 增加指定名称指定数据源的缓存
     * 
     * @param cacheName
     *            缓存名称
     * @param datasource
     *            缓存数据源
     * @throws MirrorCacheException
     */
    public synchronized void addCache(String cacheName,
            ICacheDataSource datasource) throws MirrorCacheException {
        if (caches.get(cacheName) != null) {
            logger.error("增加失败。已经存在名为[" + cacheName + "]的缓存区域");

            throw new MirrorCacheException("增加失败。已经存在名为[" + cacheName
                                           + "]的缓存区域");
        }

        String qulifyName = StringTool.qualify(getCachePrefix(), cacheName);
        this.cacheManager.addCache(qulifyName);

        IMirrorCache cache = new EHCacheImpl(this.cacheManager
                .getCache(qulifyName), datasource);
        caches.put(cacheName, cache);

        if (logger.isDebugEnabled()) {
            logger.debug("成功增加名为[" + cacheName + "]的镜像缓存");
        }
    }

    /**
     * 得到指定名称的缓存
     * 
     * @param cache
     * @return
     */
    public synchronized IMirrorCache getCache(String cacheName) {
        return caches.get(cacheName);
    }

    /**
     * 删除指定缓存
     * 
     * @param cache
     */
    public synchronized void removeCache(String cache) {
        caches.remove(cache);
        this.cacheManager.removeCache(StringTool.qualify(getCachePrefix(),
                                                         cache));
    }

    /**
     * 初始化启动方法
     */
    public void afterPropertiesSet() throws Exception {
        if (this.cacheManager == null) {
            throw new MirrorCacheException("必须设置一个底层cache管理类");
        }

        // 启动初始化的缓存项
        if (this.initCache != null && !this.initCache.isEmpty()) {
            for (Iterator each = this.initCache.keySet().iterator(); each
                    .hasNext();) {
                String key = (String) each.next();
                this.addCache(key, this.initCache.get(key));
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("镜像缓存服务类已经启动");
        }

        instance = this;
    }

    /**
     * 设置随缓存服务类启动的缓存。map的key和value分别是指定缓存名称和缓存的数据提供类
     * 
     * @param map
     */
    public void setInitCache(Map<String, ICacheDataSource> map) {
        this.initCache = map;
    }

    /**
     * 清除缓存区域的所有缓存内容。<br>
     * 特别注意：不是清除缓存区域，而是清除缓存区域的缓存内容。缓存区域不可清除。
     */
    public void clearAll() {
        this.cacheManager.clearAll();
    }

    /**
     * 销毁方法
     */
    public void shutdown() {
        this.caches.clear();
        this.cacheManager.shutdown();
    }

    /** 以下为get,set方法 */

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }
}
