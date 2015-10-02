package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.IPersistentMethod;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class DynamicEntityPersistenceServiceImpl implements DynamicEntityPersistenceService {

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    /**
     * Multi-thread support for io with EntityManager
     */
    @Resource(name = PersistenceManagerInvoker.COMPONENT_NAME)
    protected PersistenceManagerInvoker persistenceManagerIsolatedInvoker;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    @Override
    @Transactional
    public <T extends Persistable> EntityResult<T> create(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(ceilingType, entity);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> EntityResult<T> create(final Entity entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(entity);
            }
        });
    }

    @Override
    public <T extends Persistable> EntityResult<T> read(final Class<T> entityClz, final Object key) throws ServiceException{
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.read(entityClz, key);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> EntityResult<T> update(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(ceilingType, entity);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> EntityResult<T> update(final Entity entity)throws ServiceException{
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(entity);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> Void delete(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<Void, ServiceException>() {
            @Override
            public Void execute(PersistenceManager persistenceManager) throws ServiceException {
                persistenceManager.delete(ceilingType, entity);
                return null;
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> Void delete(final Entity entity, final String id)throws ServiceException{
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<Void, ServiceException>() {
            @Override
            public Void execute(PersistenceManager persistenceManager) throws ServiceException {
                persistenceManager.delete(entity, id);
                return null;
            }
        });
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(final Class<T> entityClz, final CriteriaTransferObject query)throws ServiceException{
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<CriteriaQueryResult<T>, ServiceException>() {
            @Override
            public CriteriaQueryResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.query(entityClz, query);
            }
        });
    }

}
