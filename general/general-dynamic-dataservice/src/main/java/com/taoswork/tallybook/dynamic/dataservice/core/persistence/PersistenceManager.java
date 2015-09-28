package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.NoPermissionException;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T> EntityResult<T> create(Class<T> ceilingType, T entity) throws NoPermissionException;

    <T> EntityResult<T> create(Entity entity) throws NoPermissionException;

    <T> EntityResult<T> read(Class<T> entityClz, Object key) throws NoPermissionException;

    <T> EntityResult<T> update(Class<T> ceilingType, T entity) throws NoPermissionException;

    <T> EntityResult<T> update(Entity entity) throws NoPermissionException;

    <T> void delete(Class<T> ceilingType, T entity) throws NoPermissionException;

    <T> void delete(Entity entity, String id) throws NoPermissionException;

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws NoPermissionException;

}
