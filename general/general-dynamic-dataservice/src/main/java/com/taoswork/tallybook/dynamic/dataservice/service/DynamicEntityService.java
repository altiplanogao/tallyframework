package com.taoswork.tallybook.dynamic.dataservice.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface DynamicEntityService {
    public static final String COMPONENT_NAME = "DynamicEntityService";

    @Transactional
    <T> T save(T entity);

    <T> T find(Class<T> entityClz, Object key);

    @Transactional
    <T> T update(T entity);

    @Transactional
    <T> void delete(T entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query);

    Class<?> getRootInstanceableEntityClass(Class<?> entityType);

    <T> ClassTreeMetadata inspect(Class<T> entityType);

    <T> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType);

}
