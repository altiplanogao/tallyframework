package com.taoswork.tallybook.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.EntityValidatorPool;
import com.taoswork.tallybook.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

import java.util.Collection;

public class EntityValueValidatorManager extends EntityValidatorPool implements EntityValueValidator {

    @Override
    public void validate(Persistable entity, IClassMeta classMeta, EntityValidationErrors entityValidationErrors) {
        Collection<String> validatorNames = classMeta.getReadonlyValidators();
        for (String validatorName : validatorNames) {
            IEntityValidator validator = getValidator(validatorName);
            if (validator != null) {
                validator.validate(entity, entityValidationErrors);
            }
        }
    }

}
