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
 * ���񻺴�Ķ�������ࡣ�����ʹ����ֻ��Ҫ�������򽻵���<br>
 * �û����EHCache��Ҫ��������ǿ��<br>
 * <ol>
 * <li>�ɶԻ���������м���������������ѯ��</li>
 * <li>�����ˢ�£���Ҫʱ�Զ�ˢ�»��棬��ȡ���ݡ�</li>
 * </ol>
 * �������Ϊspring�����µĵ�̬���ȿ���ͨ��spring�������������ڳ���������ṩ��̬���ʷ�ʽ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����10:57:26
 * @version 1.0
 */
public class MirrorCacheSerivce implements InitializingBean {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MirrorCacheSerivce.class);

    // ȱʡ�Ļ���ǰ׺
    public static String DEFAULT_CACHE_PREFIX = "searchableCache";

    // ��̬ʵ��
    private static MirrorCacheSerivce instance = null;

    // �ײ�cache�ṩ�ߣ�Ŀǰ��EHCache
    private CacheManager cacheManager;

    // �����ǰ׺��Ϊ����ײ�����������������
    private String cachePrefix = DEFAULT_CACHE_PREFIX;

    // �洢���пɼ�������
    private final Map<String, IMirrorCache> caches = new HashMap<String, IMirrorCache>();

    private Map<String, ICacheDataSource> initCache;

    /**
     * ��̬������������Ӧ���л�ȡ����ĵ�̬ʵ��
     * 
     * @return
     */
    public static MirrorCacheSerivce getInstance() {
        if (instance == null) {
            throw new MirrorCacheException("�ɼ�������ģ���޷����������������ã�");
        }
        return instance;
    }

    /**
     * ����ָ������ָ������Դ�Ļ���
     * 
     * @param cacheName
     *            ��������
     * @param datasource
     *            ��������Դ
     * @throws MirrorCacheException
     */
    public synchronized void addCache(String cacheName,
            ICacheDataSource datasource) throws MirrorCacheException {
        if (caches.get(cacheName) != null) {
            logger.error("����ʧ�ܡ��Ѿ�������Ϊ[" + cacheName + "]�Ļ�������");

            throw new MirrorCacheException("����ʧ�ܡ��Ѿ�������Ϊ[" + cacheName
                                           + "]�Ļ�������");
        }

        String qulifyName = StringTool.qualify(getCachePrefix(), cacheName);
        this.cacheManager.addCache(qulifyName);

        IMirrorCache cache = new EHCacheImpl(this.cacheManager
                .getCache(qulifyName), datasource);
        caches.put(cacheName, cache);

        if (logger.isDebugEnabled()) {
            logger.debug("�ɹ�������Ϊ[" + cacheName + "]�ľ��񻺴�");
        }
    }

    /**
     * �õ�ָ�����ƵĻ���
     * 
     * @param cache
     * @return
     */
    public synchronized IMirrorCache getCache(String cacheName) {
        return caches.get(cacheName);
    }

    /**
     * ɾ��ָ������
     * 
     * @param cache
     */
    public synchronized void removeCache(String cache) {
        caches.remove(cache);
        this.cacheManager.removeCache(StringTool.qualify(getCachePrefix(),
                                                         cache));
    }

    /**
     * ��ʼ����������
     */
    public void afterPropertiesSet() throws Exception {
        if (this.cacheManager == null) {
            throw new MirrorCacheException("��������һ���ײ�cache������");
        }

        // ������ʼ���Ļ�����
        if (this.initCache != null && !this.initCache.isEmpty()) {
            for (Iterator each = this.initCache.keySet().iterator(); each
                    .hasNext();) {
                String key = (String) each.next();
                this.addCache(key, this.initCache.get(key));
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("���񻺴�������Ѿ�����");
        }

        instance = this;
    }

    /**
     * �����滺������������Ļ��档map��key��value�ֱ���ָ���������ƺͻ���������ṩ��
     * 
     * @param map
     */
    public void setInitCache(Map<String, ICacheDataSource> map) {
        this.initCache = map;
    }

    /**
     * ���������������л������ݡ�<br>
     * �ر�ע�⣺��������������򣬶��������������Ļ������ݡ��������򲻿������
     */
    public void clearAll() {
        this.cacheManager.clearAll();
    }

    /**
     * ���ٷ���
     */
    public void shutdown() {
        this.caches.clear();
        this.cacheManager.shutdown();
    }

    /** ����Ϊget,set���� */

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
