package com.taoswork.tallybook.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValueGate {
    void store(IClassMeta classMeta, Persistable entity, Persistable oldEntity) throws ServiceException;

    void fetch(IClassMeta classMeta, Persistable entity);
}
