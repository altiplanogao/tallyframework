package com.taoswork.tallybook.business.datadomain.tallybusiness.validation;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessPartner;
import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.datadomain.base.entity.validation.BaseEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;

import java.util.Objects;

public class BusinessPartnerValidator extends BaseEntityValidator<BusinessPartner> {
    @Override
    protected boolean doValidate(BusinessPartner entity, EntityValidationErrors validationErrors) {
        BusinessUnit host = entity.getHost();
        BusinessUnit guest = entity.getGuest();
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
