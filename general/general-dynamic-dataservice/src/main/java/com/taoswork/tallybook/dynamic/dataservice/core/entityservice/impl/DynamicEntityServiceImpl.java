package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public <T> T save(final T entity) throws ServiceException {
        return persistenceManagerInvoker.operation(new IPersistentMethod<T, ServiceException>() {
            @Override
            public T execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.persist(entity);
            }
        });
    }

    @Override
    public <T> T find(final Class<T> entityClz, final Object key) throws ServiceException{
        return persistenceManagerInvoker.operation(new IPersistentMethod<T, ServiceException>() {
            @Override
            public T execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.find(entityClz, key);
            }
        });
    }

    @Override
    public <T> T update(final T entity)throws ServiceException{
        return persistenceManagerInvoker.operation(new IPersistentMethod<T, ServiceException>() {
            @Override
            public T execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(entity);
            }
        });
    }

    @Override
    public <T> void delete(final T entity)throws ServiceException{
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
            Constructor constructor = rootable.getConstructor(new Class[]{});
            Object obj = constructor.newInstance(new Object[]{});
            return (T)obj;
        } catch (InstantiationException e) {
            throw new ServiceException(e);
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        } catch (InvocationTargetException e) {
            throw new ServiceException(e);
        } catch (NoSuchMethodException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Class<?> getRootInstanceableEntityClass(Class<?> entityClz){
        Class<?> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return entityRootClz;
    }

    @Override
    public <T> ClassTreeMetadata inspect(Class<T> entityClz){
        return dynamicEntityMetadataAccess.getClassTreeMetadata(entityClz);
    }

    @Override
    public <T> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType, Locale locale) {
        return dynamicEntityMetadataAccess.getEntityInfo(entityType, locale, infoType);
    }

    @Override
    public Access getAuthorizeAccess(Class entityType, Access mask){
        if(mask == null)mask = Access.Crudq;
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), mask);
        return access;
    }
}
