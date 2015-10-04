package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/22.
 */

//@Secured
//@Transactional
public final class DynamicEntityServiceImpl implements DynamicEntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityServiceImpl.class);

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Resource(name = DynamicEntityPersistenceService.COMPONENT_NAME)
    protected DynamicEntityPersistenceService persistenceService;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    public DynamicEntityServiceImpl() {
    }

    private void entityAccessExceptionHandler(Exception e) throws ServiceException {
        if (e instanceof ServiceException)
            throw (ServiceException) e;
        throw new ServiceException(e);
    }

    @Override
    public <T extends Persistable> EntityResult<T> create(final Class<T> ceilingType, final T entity) throws ServiceException {
        try {
            return persistenceService.create(ceilingType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> EntityResult<T> create(final Entity entity) throws ServiceException {
        try {
            return persistenceService.create(entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> EntityResult<T> read(Class<T> entityClz, Object key) throws ServiceException {
        try {
            return persistenceService.read(entityClz, key);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> T straightRead(Class<T> entityClz, Object key) throws ServiceException {
        EntityResult<T> result = read(entityClz, key);
        return result.getEntity();
    }

    @Override
    public <T extends Persistable> EntityResult<T> update(final Class<T> ceilingType, final T entity) throws ServiceException {
        try {
            return persistenceService.update(ceilingType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> EntityResult<T> update(final Entity entity) throws ServiceException {
        try {
            return persistenceService.update(entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> boolean delete(final Class<T> ceilingType, final T entity) throws ServiceException {
        try {
            persistenceService.delete(ceilingType, entity);
            return true;
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return false;
    }

    @Override
    public <T extends Persistable> boolean delete(final Entity entity, String id) throws ServiceException {
        try {
            persistenceService.delete(entity, id);
            return true;
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
            return false;
        }
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException {
        try {
            return persistenceService.query(entityClz, query);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
            return null;
        }
    }

    @Override
    public <T extends Persistable> EntityResult<T> makeDissociatedObject(Class<T> entityClz) throws ServiceException {
        Class rootable = dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityClz);
        try {
            Persistable entity = (Persistable) rootable.newInstance();
            EntityResult<T> entityResult = new EntityResult<T>();
            Class clz = entity.getClass();
            ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(clz, false);
            Field idField = classMetadata.getIdField();
            Object id = idField.get(entity);
            entityResult.setIdKey(idField.getName())
                .setIdValue((id == null) ? null : id.toString())
                .setEntity(entity);

            return entityResult;
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Class<?> getRootInstantiableEntityClass(Class<?> entityClz) {
        Class<?> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityClz);
        return entityRootClz;
    }

    @Override
    public <T extends Persistable> ClassMetadata inspectMetadata(Class<T> entityType, boolean withHierarchy) {
        return dynamicEntityMetadataAccess.getClassMetadata(entityType, withHierarchy);
    }

    @Override
    public <T extends Persistable> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType, Locale locale) {
        boolean withHierarchy = EntityInfoType.isIncludeHierarchyByDefault(infoType);
        return this.describe(entityType, withHierarchy, infoType, locale);
    }

    @Override
    public <T extends Persistable> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale) {
        return dynamicEntityMetadataAccess.getEntityInfo(entityType, withHierarchy, locale, infoType);
    }

    @Override
    public <T extends Persistable> Access getAuthorizeAccess(Class<T> entityType, Access mask) {
        if (mask == null) mask = Access.Crudq;
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), mask);
        return access;
    }
}
