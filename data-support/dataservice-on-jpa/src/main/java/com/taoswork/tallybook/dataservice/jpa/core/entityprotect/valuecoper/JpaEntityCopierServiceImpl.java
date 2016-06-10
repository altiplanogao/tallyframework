package com.taoswork.tallybook.dataservice.jpa.core.entityprotect.valuecoper;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.descriptor.dataio.copier.CopyException;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.RFieldCopierSolution;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class JpaEntityCopierServiceImpl implements EntityCopierService {
    private final EntityCopierPool entityCopierPool;
    private final IFieldCopierSolution fieldCopierSolution;

    public JpaEntityCopierServiceImpl() {
        entityCopierPool = new EntityCopierPool();
        fieldCopierSolution = new RFieldCopierSolution(entityCopierPool);
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForQuery(CopierContext copierContext, T rec) throws ServiceException {
        try {
            return fieldCopierSolution.makeSafeCopyForQuery(rec, copierContext);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }

    @Override
    public <T extends Persistable> T makeSafeCopyForRead(CopierContext copierContext, T result) throws ServiceException {
        try {
            return fieldCopierSolution.makeSafeCopyForRead(result, copierContext);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }
}
