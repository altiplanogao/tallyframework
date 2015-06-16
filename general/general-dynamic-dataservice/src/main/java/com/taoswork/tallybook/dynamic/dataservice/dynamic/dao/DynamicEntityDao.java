package com.taoswork.tallybook.dynamic.dataservice.dynamic.dao;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;

import java.util.List;

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

    <T> T merge(T entity);

    <T> void remove(T entity);

    <T> List<T> query(Class<T> entityClz, CriteriaTransferObject query);

}
