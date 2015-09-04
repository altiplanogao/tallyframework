package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
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
    public <T> Collection<String> getAuthorizeActions(Class<T> entityType){
        List<String> actions = new ArrayList<String>();
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), Access.Crudq);
        if(access.hasGeneral(Access.CREATE))actions.add(EntityActionNames.ADD);
        if(access.hasGeneral(Access.READ))actions.add(EntityActionNames.READ);
        if(access.hasGeneral(Access.UPDATE))actions.add(EntityActionNames.UPDATE);
        if(access.hasGeneral(Access.DELETE))actions.add(EntityActionNames.DELETE);
        if(access.hasGeneral(Access.QUERY))actions.add(EntityActionNames.SEARCH);
        return actions;
    }
}
