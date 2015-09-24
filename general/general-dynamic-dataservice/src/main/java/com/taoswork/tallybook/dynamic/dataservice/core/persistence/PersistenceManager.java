package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T> EntityResult<T> create(Class<T> ceilingType, T entity);

    <T> EntityResult<T> create(Entity entity);

    <T> EntityResult<T> read(Class<T> entityClz, Object key);

    <T> EntityResult<T> update(Class<T> ceilingType, T entity);

    <T> EntityResult<T> update(Entity entity);

    <T> void delete(Class<T> ceilingType, T entity);

    <T> void delete(Entity entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query);

}
