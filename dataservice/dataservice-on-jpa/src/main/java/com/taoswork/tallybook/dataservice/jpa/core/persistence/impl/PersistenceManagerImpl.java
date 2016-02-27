package com.taoswork.tallybook.dataservice.jpa.core.persistence.impl;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.SecuredCrudqAccessor;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.core.dao.EntityDao;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerImpl
        extends SecuredCrudqAccessor
        implements PersistenceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManagerImpl.class);

    @Resource(name = EntityDao.COMPONENT_NAME)
    protected EntityDao entityDao;

    @Resource(name = EntityCopierService.COMPONENT_NAME)
    protected EntityCopierService entityCopierService;

    @Override
    protected <T extends Persistable> T doCreate(T entity) {
        return entityDao.create(entity);
    }

    @Override
    protected <T extends Persistable> T doRead(Class<T> entityRootClz, Object key) {
        T result = entityDao.read(entityRootClz, key);
        return result;
    }

    @Override
    protected <T extends Persistable> T doUpdate(T entity) {
        return entityDao.update(entity);
    }

    @Override
    protected <T extends Persistable> void doDelete(T entity) {
        entityDao.delete(entity);
    }

    @Override
    protected <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> entityRootClz, CriteriaTransferObject query) {
        CriteriaQueryResult<T> result = entityDao.query(entityRootClz, query);
        return result;
    }

    protected EntityTranslatorOnMetaAccess converter = new EntityTranslatorOnMetaAccess() {
        @Override
        protected IClassMetaAccess getClassMetaAccess() {
            return entityMetaAccess;
        }
    };

    @Override
    public <T extends Persistable> PersistableResult<T> create(Class<T> ceilingType, T entity) throws ServiceException {
        T result = securedCreate(ceilingType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.create(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityType, Object key, ExternalReference externalReference) throws ServiceException {
        T result = securedRead(entityType, key);

        CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
        T safeResult = this.entityCopierService.makeSafeCopyForRead(copierContext, result);

        return makePersistableResult(safeResult);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Class<T> ceilingType, T entity) throws ServiceException {
        T result = securedUpdate(ceilingType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.update(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> void delete(Class<T> ceilingType, T entity) throws ServiceException {
        securedDelete(ceilingType, entity);
    }

    @Override
    public <T extends Persistable> void delete(Entity entity, String id) throws ServiceException {
        Class ceilingType = getCeilingType(entity);
        T instance = (T) converter.convert(entity, id);
        this.delete(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException {
        if (query == null)
            query = new CriteriaTransferObject();
        CriteriaQueryResult<T> criteriaQueryResult = securedQuery(entityType, query);
        CriteriaQueryResult<T> safeResult = new CriteriaQueryResult<T>(criteriaQueryResult.getEntityType())
                .setStartIndex(criteriaQueryResult.getStartIndex())
                .setTotalCount(criteriaQueryResult.getTotalCount());
        List<T> records = criteriaQueryResult.getEntityCollection();
        if (records != null) {
            List<T> entities = new ArrayList();
            CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
            for (T rec : records) {
                T shallowCopy = this.entityCopierService.makeSafeCopyForQuery(copierContext, rec);
                entities.add(shallowCopy);
            }
            safeResult.setEntityCollection(entities);
        }
        return safeResult;
    }

    private <T> Class<T> getCeilingType(Entity entity) {
        return (Class<T>) entity.getCeilingType();
    }


}
