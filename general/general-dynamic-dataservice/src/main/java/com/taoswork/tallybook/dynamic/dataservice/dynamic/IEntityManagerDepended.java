package com.taoswork.tallybook.dynamic.dataservice.dynamic;

import javax.persistence.EntityManager;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
public interface IEntityManagerDepended {
    EntityManager getEntityManager();

    void setEntityManager(EntityManager entityManager);

}
