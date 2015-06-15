package com.taoswork.tallybook.general.solution.cache;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public abstract class CacheDecider {
    public long cacheTtl = 0;
    protected long lastCacheFlushTime = System.currentTimeMillis();

    public CacheDecider(long cacheTtl){
        this.cacheTtl = cacheTtl;
    }

    public long getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(long cacheTtl) {
        this.cacheTtl = cacheTtl;
    }

    public boolean useCache(){
        if (cacheTtl < 0) {
            return true;
        }
        if (cacheTtl == 0) {
            return false;
        } else {
            if ((System.currentTimeMillis() - lastCacheFlushTime) > cacheTtl) {
                lastCacheFlushTime = System.currentTimeMillis();
                clearCache();
                return true; // cache is empty
            } else {
                return true;
            }
        }
    }

    protected abstract void clearCache();
}
