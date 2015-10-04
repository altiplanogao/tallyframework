package com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.translator.EntityInstanceTranslator;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.NoSuchRecordException;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationService;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.HasHidingField;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerImpl implements PersistenceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManagerImpl.class);

    @Resource(name = DynamicEntityDao.COMPONENT_NAME)
    protected DynamicEntityDao dynamicEntityDao;

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    @Resource(name = EntityValidationService.COMPONENT_NAME)
    protected EntityValidationService entityValidationService;

    protected EntityInstanceTranslator converter = new EntityInstanceTranslator() {
        @Override
        protected DynamicEntityMetadataAccess getDynamicEntityMetadataAccess() {
            return dynamicEntityMetadataAccess;
        }
    };

    @Override
    public <T extends Persistable> EntityResult<T> create(Class<T> ceilingType, T entity) throws ServiceException {
        Class<?> guardian = this.dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Create, entity);
        if (entity instanceof HasHidingField) {
            ((HasHidingField) entity).initHidingForCreate();
        }
        EntityResult entityResult = makeEntityResult(entity);
        entityValidationService.validate(entityResult);
        T result = dynamicEntityDao.create(entity);
        return makeEntityResult(result);
    }

    @Override
    public <T extends Persistable> EntityResult<T> create(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.create(ceilingType, instance);
    }

    private <T extends Persistable> EntityResult<T> internalReadNoAccessCheck(Class<T> entityType, Object key) {
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
        T result = dynamicEntityDao.read(entityRootClz, key);
        return makeEntityResult(result);
    }

    @Override
    public <T extends Persistable> EntityResult<T> read(Class<T> entityType, Object key) throws ServiceException {
        Class<?> guardian = this.dynamicEntityMetadataAccess.getPermissionGuardian(entityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Read);
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
        T result = dynamicEntityDao.read(entityRootClz, key);
        if(result == null){
            throw new NoSuchRecordException(entityType, key);
        }
        return makeEntityResult(result);
    }

    @Override
    public <T extends Persistable> EntityResult<T> update(Class<T> ceilingType, T entity) throws ServiceException {
        Class<?> guardian = this.dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        EntityResult<T> oldEntity = getManagedEntity(ceilingType, entity);
        securityVerifier.checkAccess(guardianName, Access.Update, oldEntity.getEntity());
        securityVerifier.checkAccess(guardianName, Access.Update, entity);
        EntityResult entityResult = makeEntityResult(entity);
        entityValidationService.validate(entityResult);
        T result = dynamicEntityDao.update(entity);
        return makeEntityResult(result);
    }

    @Override
    public <T extends Persistable> EntityResult<T> update(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.update(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> void delete(Class<T> ceilingType, T entity) throws ServiceException {
        Class<?> guardian = this.dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        EntityResult<T> oldEntity = getManagedEntity(ceilingType, entity);
        if(oldEntity == null){
            EntityResult<T> temp = makeEntityResult(entity);
            throw new NoSuchRecordException(ceilingType, temp.getIdValue());
        }
        entity = oldEntity.getEntity();
        securityVerifier.checkAccess(guardianName, Access.Delete, entity);
        dynamicEntityDao.delete(entity);
    }

    @Override
    public <T extends Persistable> void delete(Entity entity, String id) throws ServiceException {
        Class ceilingType = getCeilingType(entity);
        T instance = (T) converter.convert(entity, id);
        this.delete(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query) throws ServiceException {
        Class<?> guardian = this.dynamicEntityMetadataAccess.getPermissionGuardian(entityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Query);
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
        return dynamicEntityDao.query(entityRootClz, query);
    }

    private <T extends Persistable> EntityResult<T> getManagedEntity(Class ceilingType, T entity) throws ServiceException {
        try {
            Class ceilingClz = ceilingType;
            Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstantiableEntityType(ceilingClz);
            ClassMetadata rootClzMeta = this.dynamicEntityMetadataAccess.getClassMetadata(entityRootClz, false);
            Field idField = rootClzMeta.getIdField();

            Object id = idField.get(entity);
            EntityResult oldEntity = this.internalReadNoAccessCheck(ceilingClz, id);
            return oldEntity;
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    private <T> Class<T> getCeilingType(Entity entity) {
        return (Class<T>)entity.getEntityCeilingType();
    }

    private <T extends Persistable> EntityResult<T> makeEntityResult(T entity) {
        if (entity == null)
            return null;
        EntityResult<T> entityResult = new EntityResult<T>();
        Class clz = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(clz, false);
        Field idField = classMetadata.getIdField();
        try {
            Object id = idField.get(entity);
            entityResult.setIdKey(idField.getName())
                .setIdValue((id == null) ? null : id.toString())
                .setEntity(entity);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }

        return entityResult;
    }

}
