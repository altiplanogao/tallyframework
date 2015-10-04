package com.taoswork.tallybook.general.datadomain.support.entity.validation;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

public abstract class EntityValidatorBase<T extends Persistable>
    implements IEntityValidator {

    @Override
    public final boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
        return doValidate((T)entity, validationErrors);
    }

    protected abstract boolean doValidate(T entity, EntityValidationErrors validationErrors);
}
