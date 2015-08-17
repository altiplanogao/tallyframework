package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.IPersistentMethod;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;

import javax.annotation.Resource;
import java.util.Locale;

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
}
