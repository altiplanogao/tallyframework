package com.taoswork.tallybook.general.solution.cache.ehcache;

import java.util.HashMap;

/**
 * Created by Gao Yuan on 2015/6/16.
 */
class HashCacheMap<K,V> extends HashMap<K,V> implements ICacheMap<K,V>{
    protected final String scopeName;

    public HashCacheMap(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String getScopeName() {
        return scopeName;
    }
}