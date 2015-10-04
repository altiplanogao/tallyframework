package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class CompanyValidator implements IEntityValidator{
    @Override
    public boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
        return true;
    }
}
