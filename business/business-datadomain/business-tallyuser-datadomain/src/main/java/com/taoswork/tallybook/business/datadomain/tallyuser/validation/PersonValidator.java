package com.taoswork.tallybook.business.datadomain.tallyuser.validation;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.EntityValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationErrors;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class PersonValidator extends EntityValidatorBase<Person> {
    @Override
    public boolean doValidate(Person entity, EntityValidationErrors validationErrors) {
        boolean noEmail = StringUtils.isEmpty(entity.getEmail());
        boolean noPhone = StringUtils.isEmpty(entity.getMobile());
        if(noEmail && noPhone){
            String errorCode = "person.error.email.or.phone.required";
            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError("email", errorCode);
            validationErrors.appendFieldError("mobile", errorCode);
            return false;
        }
        return true;
    }
}
