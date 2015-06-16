package com.taoswork.tallybook.general.solution.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/6/16.
 */
public class CachedRepoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachedRepoManager.class);

    private static CacheManager ehcacheCacheManager = null;

    public static void startEhcache() {
        if (ehcacheCacheManager == null) {
            ehcacheCacheManager = CacheManager.getInstance();
        }
    }

    public static void stopEhcache() {
        if (ehcacheCacheManager != null) {
            ehcacheCacheManager.shutdown();
            ehcacheCacheManager = null;
        }
    }

    public static <K, V> ICacheMap<K, V> getCacheMap(String cacheScope) {
        if (null == getEhcacheCacheManager()) {
            return new HashCacheMap<K, V>(cacheScope);
        } else {
            Cache cache = ehcacheCacheManager.getCache(cacheScope);
            if (cache == null) {
                cache = new Cache(cacheScope, 1000, true, false, 600, 1800);
                ehcacheCacheManager.addCache(cache);
            }
            return new EhcachedMap<K, V>(cacheScope, cache);
        }
    }

    private static CacheManager getEhcacheCacheManager() {
        if (null == ehcacheCacheManager) {
            LOGGER.warn("EhcachedMapManager not started.");
        }
        return ehcacheCacheManager;
    }
}
