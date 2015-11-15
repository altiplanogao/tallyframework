package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.impl;

import com.taoswork.tallybook.dynamic.dataio.copier.CopyException;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityCopierService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.CopierContext;
import com.taoswork.tallybook.dynamic.dataio.copier.EntityCopierManager;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataio.copier.EntityCopier;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class EntityCopierServiceImpl implements EntityCopierService {
    private final EntityCopierManager entityCopierManager;

    public EntityCopierServiceImpl(){
        entityCopierManager = new EntityCopierManager();
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForQuery(CopierContext copierContext, T rec) throws ServiceException {
        EntityCopier copier = new EntityCopier(copierContext.classMetadataAccess,
            copierContext.externalReference, entityCopierManager);
        try {
            return copier.makeSafeCopyForQuery(rec);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForRead(CopierContext copierContext, T result) throws ServiceException {
        EntityCopier copier = new EntityCopier(
            copierContext.classMetadataAccess,
            copierContext.externalReference, entityCopierManager);
        try {
            return copier.makeSafeCopyForRead(result);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }
}
