package com.taoswork.tallybook.datadomain.base.entity.validation;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public interface IEntityValidator {
    /**
     * @param entity
     * @param validationErrors
     * @return true if no error
     */
    boolean validate(Persistable entity, EntityValidationErrors validationErrors);
}
