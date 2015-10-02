package com.taoswork.tallybook.dynamic.dataservice.core.dao;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public interface DynamicEntityDao {
    public static final String COMPONENT_NAME = "DynamicEntityDao";

    void flush();

    void detach(Object entity);

    void refresh(Object entity);

    void clear();

    <T extends Persistable> T create(T entity);

    <T extends Persistable> T read(Class<T> entityType, Object key);

    <T extends Persistable> T update(T entity);

    <T extends Persistable> void delete(T entity);

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query);

}
