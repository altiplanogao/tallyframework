package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.general.datadomain.support.entity.validation.EntityValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationErrors;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class PersonValidator extends EntityValidatorBase<Person> {
    @Override
    public boolean doValidate(Person entity, ValidationErrors validationErrors) {
        boolean noEmail = StringUtils.isEmpty(entity.getEmail());
        boolean noPhone = StringUtils.isEmpty(entity.getMobile());
        if(noEmail && noPhone){
            validationErrors.appendError("person.error.email.or.phone.required");
            return false;
        }
        return true;
    }
}
