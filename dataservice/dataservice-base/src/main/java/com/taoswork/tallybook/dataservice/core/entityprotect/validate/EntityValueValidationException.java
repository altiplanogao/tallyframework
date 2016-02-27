package com.taoswork.tallybook.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValueValidationException extends ServiceException {
    private final PersistableResult entity;
    private EntityValidationErrors entityValidationError;

    public EntityValueValidationException(PersistableResult entity, EntityValidationErrors entityValidationError) {
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
