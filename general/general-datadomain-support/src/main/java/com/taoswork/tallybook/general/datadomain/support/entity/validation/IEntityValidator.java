package com.taoswork.tallybook.general.datadomain.support.entity.validation;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public interface IEntityValidator {
    /**
     *
     * @param entity
     * @param validationErrors
     * @return true if no error
     */
    boolean validate(Persistable entity, ValidationErrors validationErrors);
}
