package com.taoswork.tallybook.testmaterial.mongo.domain.business.dataprotect;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class CompanyValidator implements IEntityValidator {
    @Override
    public boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
        return true;
    }
}
