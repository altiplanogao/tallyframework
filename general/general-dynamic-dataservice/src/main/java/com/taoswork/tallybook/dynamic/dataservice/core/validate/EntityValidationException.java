package com.taoswork.tallybook.dynamic.dataservice.core.validate;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import org.springframework.validation.Errors;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationException extends ServiceException {
    private final EntityResult entity;
    private EntityValidationResult entityValidationResult;
    public EntityValidationException(EntityResult entity, EntityValidationResult entityValidationResult) {
        this.entity = entity;
        this.entityValidationResult = entityValidationResult;
    }

    public EntityValidationResult getEntityValidationResult() {
        return entityValidationResult;
    }

    public void setEntityValidationResult(EntityValidationResult entityValidationResult) {
        this.entityValidationResult = entityValidationResult;
    }

    public EntityResult getEntity() {
        return entity;
    }
}
