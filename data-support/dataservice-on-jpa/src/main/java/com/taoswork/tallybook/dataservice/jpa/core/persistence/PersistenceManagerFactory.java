package com.taoswork.tallybook.dataservice.jpa.core.persistence;

import com.taoswork.tallybook.general.solution.exception.UnexpectedException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerFactory implements ApplicationContextAware {
    public static final String COMPONENT_NAME = "PersistenceManagerFactory";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public PersistenceManager getPersistenceManager() {
        PersistenceManagerContext context = PersistenceManagerContext.getContext();
        if (context != null) {
            return context.getPersistenceManager();
        }
        throw new UnexpectedException("PersistenceManager getPersistenceManager");
    }

    public PersistenceManager makePersistenceManager() {
        PersistenceManager persistenceManager = (PersistenceManager) applicationContext.getBean(PersistenceManager.COMPONENT_NAME);
        return persistenceManager;
    }

    public void startPersistenceManager() {
        PersistenceManagerContext context = PersistenceManagerContext.getContext();
        if (context == null) {
            context = new PersistenceManagerContext();
            PersistenceManagerContext.addContext(context);
        }
        context.addPersistenceManager(makePersistenceManager());
    }

    public void endPersistenceManager() {
        PersistenceManagerContext context = PersistenceManagerContext.getContext();
        if (context != null) {
            context.removePersistenceManager();
        }
    }
}
