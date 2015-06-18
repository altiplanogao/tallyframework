package com.taoswork.tallybook.general.solution.cache.ehcache;

import org.apache.commons.collections.map.LRUMap;

/**
 * Created by Gao Yuan on 2015/6/18.
 */
class _CachedMapUsingLRUMap extends LRUMap implements ICacheMap{
    protected final String scopeName;

    public _CachedMapUsingLRUMap(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String getScopeName() {
        return scopeName;
    }


}
