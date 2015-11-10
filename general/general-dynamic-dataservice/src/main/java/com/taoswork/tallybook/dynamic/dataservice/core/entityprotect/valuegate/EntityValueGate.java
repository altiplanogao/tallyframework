package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValueGate {
    void store(IClassMetadata classMetadata, Persistable entity, Persistable oldEntity) throws ServiceException;

    void fetch(IClassMetadata classMetadata, Persistable entity);
}
