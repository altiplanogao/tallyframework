package com.taoswork.tallybook.general.datadomain.support.entity.validation;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public abstract class EntityValidatorBase<T extends Persistable>
    implements IEntityValidator {

    @Override
    public final boolean validate(Persistable entity, ValidationErrors validationErrors) {
        return doValidate((T)entity, validationErrors);
    }

    protected abstract boolean doValidate(T entity, ValidationErrors validationErrors);
}
