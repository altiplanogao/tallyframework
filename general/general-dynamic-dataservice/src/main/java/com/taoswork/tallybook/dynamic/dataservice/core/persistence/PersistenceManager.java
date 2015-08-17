package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";


    <T> T persist(T entity);

    <T> T find(Class<T> entityClz, Object key);

    <T> T update(T entity);

    <T> void delete(T entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query);
}
