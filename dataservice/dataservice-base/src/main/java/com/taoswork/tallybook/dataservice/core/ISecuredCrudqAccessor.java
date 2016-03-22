package com.taoswork.tallybook.dataservice.core;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public interface ISecuredCrudqAccessor {
    <T extends Persistable> T securedCreate(Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> T securedRead(Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> T securedUpdate(Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> boolean securedDelete(Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> securedQuery(Class<T> projectedEntityType, CriteriaTransferObject query) throws ServiceException;
}
