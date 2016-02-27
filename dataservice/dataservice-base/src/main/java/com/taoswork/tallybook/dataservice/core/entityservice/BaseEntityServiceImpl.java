package com.taoswork.tallybook.dataservice.core.entityservice;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class BaseEntityServiceImpl<Pb extends Persistable>
        implements IEntityService<Pb> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityServiceImpl.class);

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    protected void entityAccessExceptionHandler(Exception e) throws ServiceException {
        throw ServiceException.treatAsServiceException(e);
    }

    @Override
    public <T extends Pb> PersistableResult<T> read(Class<T> entityClz, Object key) throws ServiceException {
        return read(entityClz, key, null);
    }

    @Override
    public <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException {
        return this.query(entityClz, query, null);
    }

    @Override
    public <T extends Pb> T queryOne(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException {
        CriteriaQueryResult<T> result = this.query(entityClz, query);
        List<T> list = result.getEntityCollection();
        if(list != null && !list.isEmpty()){
            T entity = list.get(0);
            return entity;
        }
        return null;
    }

    @Override
    public <T extends Pb> T straightRead(Class<T> entityClz, Object key) throws ServiceException {
        PersistableResult<T> result = read(entityClz, key, new ExternalReference());
        return result.getValue();
    }

    @Override
    public <T extends Pb> Access getAuthorizeAccess(Class<T> entityType, Access mask) {
        if (mask == null) mask = Access.Crudq;
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), mask);
        return access;
    }

    @Override
    public <T extends Pb> PersistableResult<T> makeDissociatedPersistable(Class<T> entityClz) throws ServiceException {
        Class rootable = entityMetaAccess.getRootInstantiableEntityType(entityClz);
        try {
            PersistableResult<T> persistableResult = new PersistableResult<T>();
            T entity = (T) rootable.newInstance();
            persistableResult.setValue(entity);

            Class clz = entity.getClass();
            IClassMeta classMeta = entityMetaAccess.getClassMeta(clz, false);
            Field idField = classMeta.getIdField();
            if (idField != null) {
                Object id = idField.get(entity);
                persistableResult.setIdKey(idField.getName())
                        .setIdValue((id == null) ? null : id.toString());
            }
            Field nameField = classMeta.getNameField();
            if (nameField != null) {
                String name = (String) nameField.get(entity);
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
    public <T extends Pb> IClassMeta inspectMeta(Class<T> entityType, boolean withHierarchy) {
        return entityMetaAccess.getClassMeta(entityType, withHierarchy);
    }

    @Override
    public <T extends Pb> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale) {
        return entityMetaAccess.getEntityInfo(entityType, withHierarchy, locale, infoType);
    }
}
