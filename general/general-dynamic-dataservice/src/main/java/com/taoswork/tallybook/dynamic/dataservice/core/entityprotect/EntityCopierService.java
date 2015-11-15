package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.CopierContext;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface EntityCopierService {
    public final static String COMPONENT_NAME = "EntityCopierService";

    <T extends Persistable> T makeSafeCopyForQuery(CopierContext copierContext, T rec) throws ServiceException;

    <T extends Persistable> T makeSafeCopyForRead(CopierContext copierContext, T result) throws ServiceException;
}
