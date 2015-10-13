package com.taoswork.tallybook.dynamic.dataservice.core.persistence;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T extends Persistable> EntityResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> EntityResult<T> create(Entity entity) throws ServiceException;

    <T extends Persistable> EntityResult<T> read(Class<T> entityClz, Object key) throws ServiceException;

    <T extends Persistable> EntityResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> EntityResult<T> update(Entity entity) throws ServiceException;

    <T extends Persistable> void delete(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> void delete(Entity entity, String id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException;

}
