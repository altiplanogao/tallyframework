package com.taoswork.tallybook.dataservice.jpa.core.persistence;

import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;

import java.util.Stack;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerContext {

    private static final ThreadLocal<PersistenceManagerContext> threadPersistenceManagerContext = ThreadLocalHelper.createThreadLocal(PersistenceManagerContext.class, false);

    private final Stack<PersistenceManager> persistenceManagerStack = new Stack<PersistenceManager>();

    public static PersistenceManagerContext getContext() {
        return threadPersistenceManagerContext.get();
    }

    public static void addContext(PersistenceManagerContext persistenceManagerContext) {
        threadPersistenceManagerContext.set(persistenceManagerContext);
    }

    private static void clearContext() {
        threadPersistenceManagerContext.remove();
    }

    public void addPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManagerStack.push(persistenceManager);
    }

    public PersistenceManager getPersistenceManager() {
        return !persistenceManagerStack.empty() ? persistenceManagerStack.peek() : null;
    }

    public void removePersistenceManager() {
        if (!persistenceManagerStack.empty()) {
            persistenceManagerStack.pop();
        }
        if (persistenceManagerStack.empty()) {
            PersistenceManagerContext.clearContext();
        }
    }
}
