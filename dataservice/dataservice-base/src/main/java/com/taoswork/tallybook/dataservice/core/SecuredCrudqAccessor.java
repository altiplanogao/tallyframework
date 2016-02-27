package com.taoswork.tallybook.dataservice.core;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.NoSuchRecordException;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.EntityValidationService;
import com.taoswork.tallybook.dataservice.service.EntityValueGateService;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public abstract class SecuredCrudqAccessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecuredCrudqAccessor.class);

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    @Resource(name = EntityValidationService.COMPONENT_NAME)
    protected EntityValidationService entityValidationService;

    @Resource(name = EntityValueGateService.COMPONENT_NAME)
    protected EntityValueGateService entityValueGateService;

    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    public <T extends Persistable> T securedCreate(Class<T> ceilingType, T entity) throws ServiceException {
        if (ceilingType == null) {
            ceilingType = (Class<T>) entity.getClass();
        }
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Create, entity);
        PersistableResult persistableResult = makePersistableResult(entity);

        //store before validate, for security reason
        entityValueGateService.store(persistableResult.getValue(), null);
        entityValidationService.validate(persistableResult);

        return doCreate(entity);
    }

    protected abstract <T extends Persistable> T doCreate(T entity);

    public <T extends Persistable> T securedRead(Class<T> entityType, Object key) throws ServiceException {
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(entityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Read);
        Class<T> entityRootClz = entityMetaAccess.getRootInstantiableEntityType(entityType);
        T result = doRead(entityRootClz, key);
        if(result == null){
            throw new NoSuchRecordException(entityType, key);
        }
        entityValueGateService.fetch(result);
        return result;
    }

    protected abstract <T extends Persistable> T doRead(Class<T> entityRootClz, Object key);

    public <T extends Persistable> T securedUpdate(Class<T> ceilingType, T entity) throws ServiceException {
        if (ceilingType == null) {
            ceilingType = (Class<T>) entity.getClass();
        }
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        PersistableResult<T> oldEntity = getManagedEntity(ceilingType, entity);
        securityVerifier.checkAccess(guardianName, Access.Update, oldEntity.getValue());
        securityVerifier.checkAccess(guardianName, Access.Update, entity);
        PersistableResult persistableResult = makePersistableResult(entity);

        //store before validate, for security reason
        entityValueGateService.store(persistableResult.getValue(), oldEntity.getValue());
        entityValidationService.validate(persistableResult);

        return doUpdate(entity);
    }

    protected abstract  <T extends Persistable> T doUpdate(T entity);

    public <T extends Persistable> void securedDelete(Class<T> ceilingType, T entity) throws ServiceException {
        if (ceilingType == null) {
            ceilingType = (Class<T>) entity.getClass();
        }
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(ceilingType);
        String guardianName = guardian.getName();
        PersistableResult<T> oldEntity = getManagedEntity(ceilingType, entity);
        if(oldEntity == null){
            PersistableResult<T> temp = makePersistableResult(entity);
            throw new NoSuchRecordException(ceilingType, temp.getIdValue());
        }
        entity = oldEntity.getValue();
        securityVerifier.checkAccess(guardianName, Access.Delete, entity);
        doDelete(entity);
    }

    protected abstract <T extends Persistable> void doDelete(T entity);

    public <T extends Persistable> CriteriaQueryResult<T> securedQuery(Class<T> entityType, CriteriaTransferObject query) throws ServiceException {
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(entityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Query);
        Class<T> entityRootClz = entityMetaAccess.getRootInstantiableEntityType(entityType);
        CriteriaQueryResult<T> result = doQuery(entityRootClz, query);
        for(T one : result.getEntityCollection()){
            entityValueGateService.fetch(one);
        }
        return result;
    }

    protected abstract <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> entityRootClz, CriteriaTransferObject query);

    private <T extends Persistable> PersistableResult<T> internalReadNoAccessCheck(Class<T> entityType, Object key) {
        Class<T> entityRootClz = entityMetaAccess.getRootInstantiableEntityType(entityType);
        T result = doRead(entityRootClz, key);
        return makePersistableResult(result);
    }

    private <T extends Persistable> PersistableResult<T> getManagedEntity(Class ceilingType, T entity) throws ServiceException {
        try {
            Class ceilingClz = ceilingType;
            Class<T> entityRootClz = entityMetaAccess.getRootInstantiableEntityType(ceilingClz);
            IClassMeta rootClzMeta = entityMetaAccess.getClassMeta(entityRootClz, false);
            Field idField = rootClzMeta.getIdField();

            Object id = idField.get(entity);
            PersistableResult oldEntity = this.internalReadNoAccessCheck(ceilingClz, id);
            return oldEntity;
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public <T extends Persistable> PersistableResult<T> makePersistableResult(T entity) {
        if (entity == null)
            return null;
        PersistableResult<T> persistableResult = new PersistableResult<T>();
        Class clz = entity.getClass();
        IClassMeta classMeta = entityMetaAccess.getClassMeta(clz, false);
        Field idField = classMeta.getIdField();
        Field nameField = classMeta.getNameField();
        try {
            Object id = idField.get(entity);
            persistableResult.setIdKey(idField.getName())
                    .setIdValue((id == null) ? null : id.toString())
                    .setValue(entity);
            if(nameField != null){
                Object name = nameField.get(entity);
                persistableResult.setName((name == null) ? null : name.toString());
            }
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }

        return persistableResult;
    }

}
