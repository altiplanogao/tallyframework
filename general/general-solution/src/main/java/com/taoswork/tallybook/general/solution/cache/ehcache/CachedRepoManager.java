package com.taoswork.tallybook.general.solution.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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
            ehcacheCacheManager.removeAllCaches();
            ehcacheCacheManager.shutdown();
            ehcacheCacheManager = null;
        }
    }

    public static <K, V> ICacheMap<K, V> getUniqueCacheMap(CacheType cacheType) {
        String uuid = UUID.randomUUID().toString();
        return getCacheMap(cacheType, uuid);
    }

    public static <K, V> ICacheMap<K, V> getCacheMap(CacheType cacheType, String cacheScope) {
        switch (cacheType) {
            case LRUMap:
                return new _CachedMapUsingLRUMap(cacheScope);
            case HashMap:
                return new _CacheMapUsingHashMap<K, V>(cacheScope);
            case EhcacheCache:
                if (null == getEhcacheCacheManager()) {
                    return getDefaultCacheMap(cacheScope);
                } else {
                    Cache cache = ehcacheCacheManager.getCache(cacheScope);
                    if (cache == null) {
                        cache = new Cache(cacheScope, 1000, true, false, 600, 1800);
                        ehcacheCacheManager.addCache(cache);
                    }
                    return new _CacheMapUsingEhcache<K, V>(cacheScope, cache);
                }
            default:
                return getDefaultCacheMap(cacheScope);
        }
    }

    private static <K, V> ICacheMap<K, V> getDefaultCacheMap(String cacheScope) {
        return new _CacheMapUsingHashMap<K, V>(cacheScope);
    }


    private static CacheManager getEhcacheCacheManager() {
        if (null == ehcacheCacheManager) {
            LOGGER.warn("EhcachedMapManager not started.");
        }
        return ehcacheCacheManager;
    }
}
