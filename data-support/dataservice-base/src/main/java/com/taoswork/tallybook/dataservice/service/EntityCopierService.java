package com.taoswork.tallybook.dataservice.service;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface EntityCopierService {
    public final static String COMPONENT_NAME = "EntityCopierService";

    <T extends Persistable> T makeSafeCopyForQuery(CopierContext copierContext, T rec) throws ServiceException;

    <T extends Persistable> T makeSafeCopyForRead(CopierContext copierContext, T result) throws ServiceException;
}
