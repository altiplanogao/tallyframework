package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValueCopierService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.CopierContext;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper.EntityValueCopierManager;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.translate.CrossEntityManagerPersistableCopier;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class EntityValueCopierServiceImpl implements EntityValueCopierService {
    private final EntityValueCopierManager entityValueCopierManager;

    public EntityValueCopierServiceImpl(){
        entityValueCopierManager = new EntityValueCopierManager();
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForQuery(CopierContext copierContext, T rec) throws ServiceException {
        CrossEntityManagerPersistableCopier copier = new CrossEntityManagerPersistableCopier(copierContext.dynamicEntityMetadataAccess,
            copierContext.externalReference, entityValueCopierManager);
        return copier.makeSafeCopyForQuery(rec);
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForRead(CopierContext copierContext, T result) throws ServiceException {
        CrossEntityManagerPersistableCopier copier = new CrossEntityManagerPersistableCopier(
            copierContext.dynamicEntityMetadataAccess,
            copierContext.externalReference, entityValueCopierManager);
        return copier.makeSafeCopyForRead(result);
    }
}
