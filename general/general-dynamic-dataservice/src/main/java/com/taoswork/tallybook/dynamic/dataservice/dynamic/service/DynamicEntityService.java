package com.taoswork.tallybook.dynamic.dataservice.dynamic.service;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaTransferObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface DynamicEntityService {
    public static final String COMPONENT_NAME = "DynamicEntityService";

    Class<?> getRootPersistiveEntityClass(Class<?> entityClz);

    @Transactional
    <T> T save(T entity);

    <T> T find(Class<T> entityClz, Object key);

    @Transactional
    <T> T update(T entity);

    @Transactional
    <T> void delete(T entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query);

    <T> ClassTreeMetadata inspect(Class<T> entityClz);

}
