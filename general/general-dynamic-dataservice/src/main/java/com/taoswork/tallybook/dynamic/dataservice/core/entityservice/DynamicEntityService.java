package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface DynamicEntityService {
    public static final String COMPONENT_NAME = "DynamicEntityService";

    @Transactional
    <T> EntityResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    @Transactional
    <T> EntityResult<T> create(Entity entity) throws ServiceException;

    <T> EntityResult<T> read(Class<T> entityClz, Object key) throws ServiceException;

    <T> T straightRead(Class<T> entityClz, Object key) throws ServiceException;

    @Transactional
    <T> EntityResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    @Transactional
    <T> EntityResult<T> update(Entity entity) throws ServiceException;

    @Transactional
    <T> void delete(Class<T> ceilingType, T entity) throws ServiceException;

    @Transactional
    <T> void delete(Entity entity) throws ServiceException;

    <T> T makeDissociatedObject(Class<T> entityClz) throws ServiceException;

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException;

    Class<?> getRootInstanceableEntityClass(Class<?> entityType);

    <T> ClassMetadata inspectMetadata(Class<T> entityType, boolean withHierarchy);

    <T> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType, Locale locale);

    <T> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale);

    Access getAuthorizeAccess(Class entityType, Access mask);
}
