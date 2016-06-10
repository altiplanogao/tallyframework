package com.taoswork.tallybook.dataservice.jpa.core.dao;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public interface EntityDao {
    public static final String COMPONENT_NAME = "EntityDao";

//    void flush();
//
//    void detach(Object entity);
//
//    void refresh(Object entity);
//
//    void clear();

    <T extends Persistable> T create(T entity);

    <T extends Persistable> T read(Class<T> entityType, Object key);

    <T extends Persistable> T update(T entity);

    <T extends Persistable> void delete(T entity);

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query);

}
