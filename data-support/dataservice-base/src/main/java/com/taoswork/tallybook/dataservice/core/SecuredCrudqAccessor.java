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
public abstract class SecuredCrudqAccessor implements ISecuredCrudqAccessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecuredCrudqAccessor.class);

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    @Resource(name = EntityValidationService.COMPONENT_NAME)
    protected EntityValidationService entityValidationService;

    @Resource(name = EntityValueGateService.COMPONENT_NAME)
    protected EntityValueGateService entityValueGateService;

    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    @Override
    public <T extends Persistable> T securedCreate(Class<T> projectedEntityType, T entity) throws ServiceException {
        if (projectedEntityType == null) {
            projectedEntityType = (Class<T>) entity.getClass();
        }
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(projectedEntityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Create, entity);
        PersistableResult persistableResult = makePersistableResult(entity);

        //store before validate, for security reason
        entityValueGateService.store(persistableResult.getValue(), null);
        entityValidationService.validate(persistableResult);

        return doCreate(projectedEntityType, entity);
    }

    protected abstract <T extends Persistable> T doCreate(Class<T> projectedEntityType, T entity);

    @Override
    public <T extends Persistable> T securedRead(Class<T> projectedEntityType, Object key) throws ServiceException {
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(projectedEntityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Read);
        T result = doRead(projectedEntityType, key);
        if(result == null){
            throw new NoSuchRecordException(projectedEntityType, key);
        }
        entityValueGateService.fetch(result);
        return result;
    }

    protected abstract <T extends Persistable> T doRead(Class<T> projectedEntityType, Object key);

    @Override
    public <T extends Persistable> T securedUpdate(Class<T> projectedEntityType, T entity) throws ServiceException {
        if (projectedEntityType == null) {
            projectedEntityType = (Class<T>) entity.getClass();
        }
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(projectedEntityType);
        String guardianName = guardian.getName();
        PersistableResult<T> oldEntity = getManagedEntity(projectedEntityType, entity);
        securityVerifier.checkAccess(guardianName, Access.Update, oldEntity.getValue());
        securityVerifier.checkAccess(guardianName, Access.Update, entity);
        PersistableResult persistableResult = makePersistableResult(entity);

        //store before validate, for security reason
        entityValueGateService.store(persistableResult.getValue(), oldEntity.getValue());
        entityValidationService.validate(persistableResult);

        return doUpdate(projectedEntityType, entity);
    }

    protected abstract  <T extends Persistable> T doUpdate(Class<T> projectedEntityType, T entity);

    @Override
    public <T extends Persistable> boolean securedDelete(Class<T> projectedEntityType, Object id) throws ServiceException {
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(projectedEntityType);
        String guardianName = guardian.getName();
        PersistableResult<T> oldEntity = getManagedEntityById(projectedEntityType, id);
        if(oldEntity == null){
            throw new NoSuchRecordException(projectedEntityType, id);
        }
        Persistable entity = oldEntity.getValue();
        securityVerifier.checkAccess(guardianName, Access.Delete, entity);
        return doDelete(projectedEntityType, (T)entity);
    }

    protected abstract <T extends Persistable> boolean doDelete(Class<T> projectedEntityType, T entity);

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> securedQuery(Class<T> projectedEntityType, CriteriaTransferObject query) throws ServiceException {
        Class<?> guardian = entityMetaAccess.getPermissionGuardian(projectedEntityType);
        String guardianName = guardian.getName();
        securityVerifier.checkAccess(guardianName, Access.Query);
        CriteriaQueryResult<T> result = doQuery(projectedEntityType, query);
        for(T one : result.getEntityCollection()){
            entityValueGateService.fetch(one);
        }
        return result;
    }

    protected abstract <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> projectedEntityType, CriteriaTransferObject query);

    private <T extends Persistable> PersistableResult<T> internalReadNoAccessCheck(Class<T> projectedEntityType, Object key) {
        T result = doRead(projectedEntityType, key);
        return makePersistableResult(result);
    }

    private <T extends Persistable> Object getEntityId(Class projectedEntityType, T entity) throws ServiceException {
        try {
            IClassMeta rootClzMeta = entityMetaAccess.getClassMeta(projectedEntityType, false);
            Field idField = rootClzMeta.getIdField();

            Object id = idField.get(entity);
            return id;
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    private <T extends Persistable> PersistableResult<T> getManagedEntityById(Class projectedEntityType, Object id) throws ServiceException {
        PersistableResult oldEntity = this.internalReadNoAccessCheck(projectedEntityType, id);
        return oldEntity;
    }

    private <T extends Persistable> PersistableResult<T> getManagedEntity(Class projectedEntityType, T entity) throws ServiceException {
        Object id = getEntityId(projectedEntityType, entity);
        return getManagedEntityById(projectedEntityType, id);
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
