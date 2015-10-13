package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface EntityValueGateService {
    public final static String COMPONENT_NAME = "EntityValueGateService";

    <T extends Persistable> void deposit(T entity, T oldEntity) throws ServiceException;

    <T extends Persistable> void withdraw(T entity);
}
