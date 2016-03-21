package com.taoswork.tallybook.dataservice.jpa.core.entityservice.impl;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.entityservice.BaseEntityServiceImpl;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
//import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/22.
 */

//@Secured
//@Transactional
public final class JpaEntityServiceImpl
        extends BaseEntityServiceImpl<Persistable>
        implements JpaEntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaEntityServiceImpl.class);

    @Resource(name = DynamicEntityPersistenceService.COMPONENT_NAME)
    protected DynamicEntityPersistenceService persistenceService;

    public JpaEntityServiceImpl() {
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(final T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);

            return persistenceService.create(projectedEntityType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            return persistenceService.read(projectedEntityType, key, externalReference);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(final T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);
            return persistenceService.update(projectedEntityType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> boolean delete(final T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);
            persistenceService.delete(projectedEntityType, entity);
            return true;
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return false;
    }

//    @Override
//    public <T extends Persistable> boolean delete(final Entity entity, String id) throws ServiceException {
//        try {
//            persistenceService.delete(entity, id);
//            return true;
//        } catch (Exception e) {
//            entityAccessExceptionHandler(e);
//            return false;
//        }
//    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            return persistenceService.query(projectedEntityType, query, externalReference);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
            return null;
        }
    }

    protected Class<?> getProjectedEntityType(Class<?> entityClz) {
        Class<?> entityRootClz = this.entityMetaAccess.getRootInstantiableEntityType(entityClz);
        return entityRootClz;
    }

}
