package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.IPersistentMethod;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.authority.core.basic.Access;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public final class DynamicEntityServiceImpl implements DynamicEntityService {
    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = PersistenceManagerInvoker.COMPONENT_NAME)
    protected PersistenceManagerInvoker persistenceManagerInvoker;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    public DynamicEntityServiceImpl(){
    }

    @Override
    public <T> EntityResult<T> create(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(ceilingType, entity);
            }
        });
    }

    @Override
    public <T> EntityResult<T> create(final Entity entity) throws ServiceException {
        return persistenceManagerInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(entity);
            }
        });
    }

    @Override
    public <T> EntityResult<T> read(final Class<T> entityClz, final Object key) throws ServiceException{
        return persistenceManagerInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.read(entityClz, key);
            }
        });
    }

    @Override
    public <T> T straightRead(Class<T> entityClz, Object key) throws ServiceException {
        EntityResult<T> result = read(entityClz, key);
        return result.getEntity();
    }

    @Override
    public <T> EntityResult<T> update(final Class<T> ceilingType, final T entity) throws ServiceException {
        return persistenceManagerInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(ceilingType, entity);
            }
        });
    }

    @Override
    public <T> EntityResult<T> update(final Entity entity)throws ServiceException{
        return persistenceManagerInvoker.operation(new IPersistentMethod<EntityResult<T>, ServiceException>() {
            @Override
            public EntityResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(entity);
            }
        });
    }

    @Override
    public <T> void delete(final Class<T> ceilingType, final T entity) throws ServiceException {
        persistenceManagerInvoker.operation(new IPersistentMethod<Void, ServiceException>() {
            @Override
            public Void execute(PersistenceManager persistenceManager) throws ServiceException {
                persistenceManager.delete(ceilingType, entity);
                return null;
            }
        });

    }

    @Override
    public <T> void delete(final Entity entity)throws ServiceException{
        persistenceManagerInvoker.operation(new IPersistentMethod<Void, ServiceException>() {
            @Override
            public Void execute(PersistenceManager persistenceManager) throws ServiceException {
                persistenceManager.delete(entity);
                return null;
            }
        });
    }

    @Override
    public <T> CriteriaQueryResult<T> query(final Class<T> entityClz, final CriteriaTransferObject query)throws ServiceException{
        return persistenceManagerInvoker.operation(new IPersistentMethod<CriteriaQueryResult<T>, ServiceException>() {
            @Override
            public CriteriaQueryResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.query(entityClz, query);
            }
        });
    }

    @Override
    public <T> T makeDissociatedObject(Class<T> entityClz) throws ServiceException {
        Class rootable = dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        try {
            Object obj = rootable.newInstance();
            return (T)obj;
        } catch (InstantiationException e) {
            throw new ServiceException(e);
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Class<?> getRootInstanceableEntityClass(Class<?> entityClz){
        Class<?> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return entityRootClz;
    }

    @Override
    public <T> ClassMetadata inspectMetadata(Class<T> entityType, boolean withHierarchy){
        return dynamicEntityMetadataAccess.getClassMetadata(entityType, withHierarchy);
    }

    @Override
    public <T> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType, Locale locale) {
        boolean withHierarchy = EntityInfoType.isIncludeHierarchyByDefault(infoType);
        return this.describe(entityType, withHierarchy, infoType, locale);
    }

    @Override
    public <T> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale) {
        return dynamicEntityMetadataAccess.getEntityInfo(entityType, withHierarchy, locale, infoType);
    }

    @Override
    public Access getAuthorizeAccess(Class entityType, Access mask){
        if(mask == null)mask = Access.Crudq;
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), mask);
        return access;
    }
}
