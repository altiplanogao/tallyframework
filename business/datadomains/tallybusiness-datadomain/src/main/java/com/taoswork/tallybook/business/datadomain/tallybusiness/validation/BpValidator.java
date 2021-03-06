package com.taoswork.tallybook.business.datadomain.tallybusiness.validation;

import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Bp;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Bu;
import com.taoswork.tallycheck.datadomain.base.entity.validation.BaseEntityValidator;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;

import java.util.Objects;

public class BpValidator extends BaseEntityValidator<Bp> {
    @Override
    protected boolean doValidate(Bp entity, EntityValidationErrors validationErrors) {
        Bu host = entity.getHost();
        Bu guest = entity.getGuest();
        if (host != null && guest != null) {
            if (Objects.equals(host.getId(), guest.getId())) {
                return true;
            } else {
                String errorCode = "businesspartner.bp.is.self";
                validationErrors.appendError(errorCode);
                validationErrors.appendFieldError("host", errorCode);
                validationErrors.appendFieldError("guest", errorCode);
                return false;
            }
        }
        return false;
    }
}
