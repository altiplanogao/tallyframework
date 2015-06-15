package com.taoswork.tallybook.testframework.persistence.em;

import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class EntityManagerHolder {
    @PersistenceContext(unitName = TestDbPersistenceConfig.TEST_DB_PU_NAME)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
