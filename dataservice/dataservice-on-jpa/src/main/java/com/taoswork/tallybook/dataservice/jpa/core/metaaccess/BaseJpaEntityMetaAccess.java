package com.taoswork.tallybook.dataservice.jpa.core.metaaccess;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.core.metaaccess.helper.EntityMetaRawAccess;
import com.taoswork.tallybook.dataservice.core.metaaccess.impl.BaseEntityMetaAccess;

import javax.persistence.EntityManager;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public abstract class BaseJpaEntityMetaAccess
        extends BaseEntityMetaAccess
        implements JpaEntityMetaAccess {

    public abstract EntityManager getEntityManager();

    @Override
    protected Class<?> superPersistable() {
        return Persistable.class;
    }

    @Override
    public EntityMetaRawAccess makeEntityMetaRawAccess() {
        EntityManager entityManager = getEntityManager();
        JpaEntityMetaRawAccess rawAccessJPA = new JpaEntityMetaRawAccess();
        rawAccessJPA.setEntityManager(entityManager);
        return rawAccessJPA;
    }

}
