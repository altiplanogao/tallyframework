package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface DynamicEntityPersistenceService {
    public static final String COMPONENT_NAME = "DynamicEntityPersistenceService";

    @Transactional
    <T extends Persistable> EntityResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    @Transactional
    <T extends Persistable> EntityResult<T> create(Entity entity) throws ServiceException;

    <T extends Persistable> EntityResult<T> read(Class<T> entityClz, Object key) throws ServiceException;

    @Transactional
    <T extends Persistable> EntityResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    @Transactional
    <T extends Persistable> EntityResult<T> update(Entity entity)throws ServiceException;

    @Transactional
    <T extends Persistable> Void delete(Class<T> ceilingType, T entity) throws ServiceException;

    /**
     *
     * @param entity
     * @param id: OPTIONAL, used if param entity doesn't contains id field
     * @param <T>
     * @return
     * @throws ServiceException
     */
    @Transactional
    <T extends Persistable> Void delete(Entity entity, String id)throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query)throws ServiceException;
}
