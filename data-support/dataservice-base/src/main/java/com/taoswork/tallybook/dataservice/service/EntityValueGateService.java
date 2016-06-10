package com.taoswork.tallybook.dataservice.service;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface EntityValueGateService {
    public final static String COMPONENT_NAME = "EntityValueGateService";

    <T extends Persistable> void store(T entity, T oldEntity) throws ServiceException;

    <T extends Persistable> void fetch(T entity);
}
