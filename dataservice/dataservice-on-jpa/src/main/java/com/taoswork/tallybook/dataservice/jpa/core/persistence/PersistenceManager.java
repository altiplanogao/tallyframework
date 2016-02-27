package com.taoswork.tallybook.dataservice.jpa.core.persistence;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T extends Persistable> PersistableResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> void delete(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference) throws ServiceException;


    <T extends Persistable> PersistableResult<T> create(Entity entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(Entity entity) throws ServiceException;

    <T extends Persistable> void delete(Entity entity, String id) throws ServiceException;

}
