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
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
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
    protected <T extends Persistable> T doCreate(Class<T> projectedEntityType, T entity) {
        return entityDao.create(entity);
    }

    @Override
    protected <T extends Persistable> T doRead(Class<T> projectedEntityType, Object key) {
        T result = entityDao.read(projectedEntityType, key);
        return result;
    }

    @Override
    protected <T extends Persistable> T doUpdate(Class<T> projectedEntityType, T entity) {
        return entityDao.update(entity);
    }

    @Override
    protected <T extends Persistable> boolean doDelete(Class<T> projectedEntityType, T entity) {
        entityDao.delete(entity);
        return true;
    }

    @Override
    protected <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> projectedEntityType, CriteriaTransferObject query) {
        CriteriaQueryResult<T> result = entityDao.query(projectedEntityType, query);
        return result;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(Class<T> projectedEntityType, T entity) throws ServiceException {
        T result = securedCreate(projectedEntityType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> projectedEntityType, Object key, ExternalReference externalReference) throws ServiceException {
        T result = securedRead(projectedEntityType, key);

        CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
        T safeResult = this.entityCopierService.makeSafeCopyForRead(copierContext, result);

        return makePersistableResult(safeResult);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Class<T> projectedEntityType, T entity) throws ServiceException {
        T result = securedUpdate(projectedEntityType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> boolean delete(Class<T> projectedEntityType, Object key) throws ServiceException {
        return securedDelete(projectedEntityType, key);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> projectedEntityType, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException {
        if (query == null)
            query = new CriteriaTransferObject();
        CriteriaQueryResult<T> criteriaQueryResult = securedQuery(projectedEntityType, query);
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

}
