package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;

import java.util.Stack;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerContext {

    private static final ThreadLocal<PersistenceManagerContext> sPersistenceManagerContext = ThreadLocalHelper.createThreadLocal(PersistenceManagerContext.class, false);

    public static PersistenceManagerContext getPersistenceManagerContext() {
        return sPersistenceManagerContext.get();
    }

    public static void addPersistenceManagerContext(PersistenceManagerContext persistenceManagerContext) {
        sPersistenceManagerContext.set(persistenceManagerContext);
    }

    private static void clear() {
        sPersistenceManagerContext.remove();
    }

    private final Stack<PersistenceManager> persistenceManager = new Stack<PersistenceManager>();

    public void addPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager.add(persistenceManager);
    }

    public PersistenceManager getPersistenceManager() {
        return !persistenceManager.empty()?persistenceManager.peek():null;
    }

    public void remove() {
        if (!persistenceManager.empty()) {
            persistenceManager.pop();
        }
        if (persistenceManager.empty()) {
            PersistenceManagerContext.clear();
        }
    }
}
