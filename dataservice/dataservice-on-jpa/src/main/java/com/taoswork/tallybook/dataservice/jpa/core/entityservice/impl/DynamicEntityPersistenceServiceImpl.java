package com.taoswork.tallybook.dataservice.jpa.core.entityservice.impl;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.JpaEntityMetaAccess;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.IPersistentMethod;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

//Aspected by OpenEntityManagerAop.java
public class DynamicEntityPersistenceServiceImpl implements DynamicEntityPersistenceService {

    @Resource(name = JpaEntityMetaAccess.COMPONENT_NAME)
    protected JpaEntityMetaAccess entityMetaAccess;

    /**
     * Multi-thread support for io with EntityManager
     */
    @Resource(name = PersistenceManagerInvoker.COMPONENT_NAME)
    protected PersistenceManagerInvoker persistenceManagerIsolatedInvoker;
//
//    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
//    protected ISecurityVerifier securityVerifier;

    @Override
    @Transactional
    public <T extends Persistable> PersistableResult<T> create(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(ceilingType, entity);
            }
        });
    }

//    @Override
//    @Transactional
//    public <T extends Persistable> PersistableResult<T> create(final Entity entity) throws ServiceException {
//        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
//            @Override
//            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
//                return persistenceManager.create(entity);
//            }
//        });
//    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(final Class<T> entityClz, final Object key, final ExternalReference externalReference) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.read(entityClz, key, externalReference);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> PersistableResult<T> update(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(ceilingType, entity);
            }
        });
    }

//    @Override
//    @Transactional
//    public <T extends Persistable> PersistableResult<T> update(final Entity entity) throws ServiceException {
//        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
//            @Override
//            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
//                return persistenceManager.update(entity);
//            }
//        });
//    }

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

//    @Override
//    @Transactional
//    public <T extends Persistable> Void delete(final Entity entity, final String id) throws ServiceException {
//        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<Void, ServiceException>() {
//            @Override
//            public Void execute(PersistenceManager persistenceManager) throws ServiceException {
//                persistenceManager.delete(entity, id);
//                return null;
//            }
//        });
//    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(final Class<T> entityClz, final CriteriaTransferObject query, final ExternalReference externalReference) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<CriteriaQueryResult<T>, ServiceException>() {
            @Override
            public CriteriaQueryResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.query(entityClz, query, externalReference);
            }
        });
    }

}
