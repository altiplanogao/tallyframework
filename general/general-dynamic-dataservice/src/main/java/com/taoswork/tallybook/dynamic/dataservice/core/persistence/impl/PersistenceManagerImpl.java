package com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.ExternalReference;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.translator.EntityInstanceTranslator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValueGateService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.NoSuchRecordException;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.translate.CrossEntityManagerPersistableCopier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    @Resource(name = EntityValueGateService.COMPONENT_NAME)
    protected EntityValueGateService entityValueGateService;

    protected class UnsafePersistence{
        public <T extends Persistable> T doCreate(Class<T> ceilingType, T entity) throws ServiceException {
            if (ceilingType == null) {
                ceilingType = (Class<T>) entity.getClass();
            }
            Class<?> guardian = dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
            String guardianName = guardian.getName();
            securityVerifier.checkAccess(guardianName, Access.Create, entity);
            PersistableResult persistableResult = makePersistableResult(entity);

            //store before validate, for security reason
            entityValueGateService.store(persistableResult.getEntity(), null);
            entityValidationService.validate(persistableResult);

            return dynamicEntityDao.create(entity);
        }

        public <T extends Persistable> T doRead(Class<T> entityType, Object key) throws ServiceException {
            Class<?> guardian = dynamicEntityMetadataAccess.getPermissionGuardian(entityType);
            String guardianName = guardian.getName();
            securityVerifier.checkAccess(guardianName, Access.Read);
            Class<T> entityRootClz = dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
            T result = dynamicEntityDao.read(entityRootClz, key);
            if(result == null){
                throw new NoSuchRecordException(entityType, key);
            }
            entityValueGateService.fetch(result);
            return result;
        }

        public <T extends Persistable> T doUpdate(Class<T> ceilingType, T entity) throws ServiceException {
            if (ceilingType == null) {
                ceilingType = (Class<T>) entity.getClass();
            }
            Class<?> guardian = dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
            String guardianName = guardian.getName();
            PersistableResult<T> oldEntity = getManagedEntity(ceilingType, entity);
            securityVerifier.checkAccess(guardianName, Access.Update, oldEntity.getEntity());
            securityVerifier.checkAccess(guardianName, Access.Update, entity);
            PersistableResult persistableResult = makePersistableResult(entity);

            //store before validate, for security reason
            entityValueGateService.store(persistableResult.getEntity(), oldEntity.getEntity());
            entityValidationService.validate(persistableResult);

            return dynamicEntityDao.update(entity);
        }

        public <T extends Persistable> void doDelete(Class<T> ceilingType, T entity) throws ServiceException {
            if (ceilingType == null) {
                ceilingType = (Class<T>) entity.getClass();
            }
            Class<?> guardian = dynamicEntityMetadataAccess.getPermissionGuardian(ceilingType);
            String guardianName = guardian.getName();
            PersistableResult<T> oldEntity = getManagedEntity(ceilingType, entity);
            if(oldEntity == null){
                PersistableResult<T> temp = makePersistableResult(entity);
                throw new NoSuchRecordException(ceilingType, temp.getIdValue());
            }
            entity = oldEntity.getEntity();
            securityVerifier.checkAccess(guardianName, Access.Delete, entity);
            dynamicEntityDao.delete(entity);
        }

        public <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> entityType, CriteriaTransferObject query) throws ServiceException {
            Class<?> guardian = dynamicEntityMetadataAccess.getPermissionGuardian(entityType);
            String guardianName = guardian.getName();
            securityVerifier.checkAccess(guardianName, Access.Query);
            Class<T> entityRootClz = dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
            CriteriaQueryResult<T> result = dynamicEntityDao.query(entityRootClz, query);
            for(T one : result.getEntityCollection()){
                entityValueGateService.fetch(one);
            }
            return result;
        }

        private <T extends Persistable> PersistableResult<T> getManagedEntity(Class ceilingType, T entity) throws ServiceException {
            try {
                Class ceilingClz = ceilingType;
                Class<T> entityRootClz = dynamicEntityMetadataAccess.getRootInstantiableEntityType(ceilingClz);
                ClassMetadata rootClzMeta = dynamicEntityMetadataAccess.getClassMetadata(entityRootClz, false);
                Field idField = rootClzMeta.getIdField();

                Object id = idField.get(entity);
                PersistableResult oldEntity = this.internalReadNoAccessCheck(ceilingClz, id);
                return oldEntity;
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage());
                throw new ServiceException(e);
            }
        }

        private <T extends Persistable> PersistableResult<T> internalReadNoAccessCheck(Class<T> entityType, Object key) {
            Class<T> entityRootClz = dynamicEntityMetadataAccess.getRootInstantiableEntityType(entityType);
            T result = dynamicEntityDao.read(entityRootClz, key);
            return makePersistableResult(result);
        }

        public <T extends Persistable> PersistableResult<T> makePersistableResult(T entity) {
            if (entity == null)
                return null;
            PersistableResult<T> persistableResult = new PersistableResult<T>();
            Class clz = entity.getClass();
            ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(clz, false);
            Field idField = classMetadata.getIdField();
            Field nameField = classMetadata.getNameField();
            try {
                Object id = idField.get(entity);
                persistableResult.setIdKey(idField.getName())
                    .setIdValue((id == null) ? null : id.toString())
                    .setEntity(entity);
                if(nameField != null){
                    Object name = nameField.get(entity);
                    persistableResult.setEntityName((name == null) ? null : name.toString());
                }
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage());
            }

            return persistableResult;
        }
    }


    private final UnsafePersistence unsafePm = new UnsafePersistence();

    protected EntityInstanceTranslator converter = new EntityInstanceTranslator() {
        @Override
        protected DynamicEntityMetadataAccess getDynamicEntityMetadataAccess() {
            return dynamicEntityMetadataAccess;
        }
    };

    @Override
    public <T extends Persistable> PersistableResult<T> create(Class<T> ceilingType, T entity) throws ServiceException {
        T result = unsafePm.doCreate(ceilingType, entity);
        return unsafePm.makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.create(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityType, Object key, ExternalReference externalReference) throws ServiceException {
        T result = unsafePm.doRead(entityType, key);
        CrossEntityManagerPersistableCopier copier = new CrossEntityManagerPersistableCopier(this.dynamicEntityMetadataAccess, externalReference);
        T safeResult = copier.makeSafeCopyForRead(result);

        return unsafePm.makePersistableResult(safeResult);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Class<T> ceilingType, T entity) throws ServiceException {
        T result = unsafePm.doUpdate(ceilingType, entity);
        return unsafePm.makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Entity entity) throws ServiceException {
        T instance = (T) converter.convert(entity, null);
        Class ceilingType = getCeilingType(entity);
        return this.update(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> void delete(Class<T> ceilingType, T entity) throws ServiceException {
        unsafePm.doDelete(ceilingType, entity);
    }

    @Override
    public <T extends Persistable> void delete(Entity entity, String id) throws ServiceException {
        Class ceilingType = getCeilingType(entity);
        T instance = (T) converter.convert(entity, id);
        this.delete(ceilingType, instance);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException {
        if(query == null)
            query = new CriteriaTransferObject();
        CriteriaQueryResult<T> criteriaQueryResult = unsafePm.doQuery(entityType, query);
        CriteriaQueryResult<T> safeResult = new CriteriaQueryResult<T>(criteriaQueryResult.getEntityType())
            .setStartIndex(criteriaQueryResult.getStartIndex())
            .setTotalCount(criteriaQueryResult.getTotalCount());
        List<T> records = criteriaQueryResult.getEntityCollection();
        if(records != null){
            List<T> entities = new ArrayList();
            CrossEntityManagerPersistableCopier copier = new CrossEntityManagerPersistableCopier(this.dynamicEntityMetadataAccess, externalReference);
            for(T rec : records){
                T shallowCopy = copier.makeSafeCopyForQuery(rec);
                entities.add(shallowCopy);
            }
            safeResult.setEntityCollection(entities);
        }
        return safeResult;
    }

    private <T> Class<T> getCeilingType(Entity entity) {
        return (Class<T>)entity.getEntityCeilingType();
    }


}
