package com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataio.reference.ExternalReference;
import com.taoswork.tallybook.dynamic.dataio.reference.PersistableResult;
import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Locale;

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
        throw ServiceException.treatAsServiceException(e);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(final Class<T> ceilingType, final T entity) throws ServiceException {
        try {
            return persistenceService.create(ceilingType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(final Entity entity) throws ServiceException {
        try {
            return persistenceService.create(entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key) throws ServiceException {
        return read(entityClz, key, null);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException {
        try {
            return persistenceService.read(entityClz, key, externalReference);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> T straightRead(Class<T> entityClz, Object key) throws ServiceException {
        PersistableResult<T> result = read(entityClz, key, new ExternalReference());
        return result.getValue();
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(final Class<T> ceilingType, final T entity) throws ServiceException {
        try {
            return persistenceService.update(ceilingType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(final Entity entity) throws ServiceException {
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
        return this.query(entityClz, query, null);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException {
        try {
            return persistenceService.query(entityClz, query, externalReference);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
            return null;
        }
    }

    @Override
    public <T extends Persistable> PersistableResult<T> makeDissociatedPersistable(Class<T> entityClz) throws ServiceException {
        Class rootable = dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityClz);
        try {
            PersistableResult<T> persistableResult = new PersistableResult<T>();
            T entity = (T) rootable.newInstance();
            persistableResult.setValue(entity);

            Class clz = entity.getClass();
            IClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(clz, false);
            Field idField = classMetadata.getIdField();
            if(idField != null){
                Object id = idField.get(entity);
                persistableResult.setIdKey(idField.getName())
                    .setIdValue((id == null) ? null : id.toString());
            }
            Field nameField = classMetadata.getNameField();
            if (nameField != null){
                String name = (String)nameField.get(entity);
                persistableResult.setName(name);
            }

            return persistableResult;
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
    public <T extends Persistable> IClassMetadata inspectMetadata(Class<T> entityType, boolean withHierarchy) {
        return dynamicEntityMetadataAccess.getClassMetadata(entityType, withHierarchy);
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
