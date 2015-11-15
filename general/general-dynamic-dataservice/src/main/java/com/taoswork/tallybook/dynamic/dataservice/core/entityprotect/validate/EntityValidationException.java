package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.dynamic.dataio.reference.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationException extends ServiceException {
    private final PersistableResult entity;
    private EntityValidationErrors entityValidationError;

    public EntityValidationException(PersistableResult entity, EntityValidationErrors entityValidationError) {
        this.entity = entity;
        this.entityValidationError = entityValidationError;
    }

    public EntityValidationErrors getEntityValidationError() {
        return entityValidationError;
    }

    public void setEntityValidationError(EntityValidationErrors entityValidationError) {
        this.entityValidationError = entityValidationError;
    }

    public PersistableResult getEntity() {
        return entity;
    }
}
