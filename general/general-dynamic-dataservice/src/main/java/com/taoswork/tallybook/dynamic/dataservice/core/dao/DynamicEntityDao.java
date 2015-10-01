package com.taoswork.tallybook.dynamic.dataservice.core.dao;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public interface DynamicEntityDao {
    public static final String COMPONENT_NAME = "DynamicEntityDao";

    void flush();

    void detach(Object entity);

    void refresh(Object entity);

    void clear();

    <T> T create(T entity);

    <T> T read(Class<T> entityType, Object key);

    <T> T update(T entity);

    <T> void delete(T entity);

    <T> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query);

}
