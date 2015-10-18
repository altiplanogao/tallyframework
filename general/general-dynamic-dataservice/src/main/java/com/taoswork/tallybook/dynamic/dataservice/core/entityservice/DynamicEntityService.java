package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.ExternalReference;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Locale;

/**
 * Entry of data access.
 *
 * DynamicEntityService(DynamicEntityServiceImpl)
 * ()
 *      |
 *      |
 *     \/
 * DynamicEntityPersistenceService(DynamicEntityPersistenceServiceImpl)
 * ({@link com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService})
 * (Aspected by OpenSessionAop.java)
 * (@Transactional)
 * (ThreadSafe by PersistenceManagerInvoker)
 *      |
 *      |
 *     \/
 * PersistenceManager(PersistenceManagerImpl)
 * ({@link com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager})
 * (Security Check)
 * (Value Gate)
 * (Value Validation)
 *
 */
public interface DynamicEntityService {
    public static final String COMPONENT_NAME = "DynamicEntityService";

    <T extends Persistable> PersistableResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> create(Entity entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Persistable> T straightRead(Class<T> entityClz, Object key) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(Entity entity) throws ServiceException;

    <T extends Persistable> boolean delete(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> boolean delete(Entity entity, String id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException;

    <T extends Persistable> PersistableResult<T> makeDissociatedObject(Class<T> entityClz) throws ServiceException;

    Class<?> getRootInstantiableEntityClass(Class<?> entityType);

    <T extends Persistable> ClassMetadata inspectMetadata(Class<T> entityType, boolean withHierarchy);

    <T extends Persistable> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType, Locale locale);

    <T extends Persistable> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale);

    <T extends Persistable> Access getAuthorizeAccess(Class<T> entityType, Access mask);
}
