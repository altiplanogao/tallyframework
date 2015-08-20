package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface IPersistentMethod<V, Error extends Throwable> {
    public V execute(PersistenceManager persistenceManager) throws Error;
}