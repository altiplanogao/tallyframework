package com.taoswork.tallybook.dynamic.dataservice.dao;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public interface DynamicEntityDao {
    public static final String COMPONENT_NAME = "DynamicEntityDao";

    void flush();

    void detach(Object entity);

    void refresh(Object entity);

    void clear();

    <T> T persist(T entity);

    <T> T find(Class<T> entityClz, Object key);

    <T> T update(T entity);

    <T> void remove(T entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query);

}
