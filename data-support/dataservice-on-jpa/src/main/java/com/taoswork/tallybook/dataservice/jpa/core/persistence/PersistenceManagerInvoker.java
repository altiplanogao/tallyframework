package com.taoswork.tallybook.dataservice.jpa.core.persistence;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerInvoker {

    public static final String COMPONENT_NAME = "PersistenceManagerInvoker";

    @Resource(name = PersistenceManagerFactory.COMPONENT_NAME)
    protected PersistenceManagerFactory persistenceManagerFactory;

    public <V, Error extends Throwable> V operation(IPersistentMethod<V, Error> persistentMethod) throws Error {
        try {
            persistenceManagerFactory.startPersistenceManager();
            return persistentMethod.execute(persistenceManagerFactory.getPersistenceManager());
        } finally {
            persistenceManagerFactory.endPersistenceManager();
        }
    }
}
