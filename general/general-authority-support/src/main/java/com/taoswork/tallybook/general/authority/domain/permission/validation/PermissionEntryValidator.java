package com.taoswork.tallybook.general.authority.domain.permission.validation;

import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.EntityValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/10/28.
 */
public class PermissionEntryValidator extends EntityValidatorBase<PermissionEntry> {
    @Override
    protected boolean doValidate(PermissionEntry entity, EntityValidationErrors validationErrors) {
        SecuredResource resource = entity.getSecuredResource();
        SecuredResourceSpecial resourceSpecial = entity.getSecuredResourceSpecial();
        Long resourceId = null;
        Long resourceSpecialId = null;
        if(resource != null){
            resourceId = resource.getId();
        }
        if(resourceSpecial != null){
            resourceSpecialId = resourceSpecial.getId();
        }
        if(resourceId == null && resourceSpecialId == null){
            String errorCode = "permission.entry.error.need.resource.or.resourcespecial";

            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError("securedResource", errorCode);
            validationErrors.appendFieldError("securedResourceSpecial", errorCode);
            return false;
        }

        if(resourceId != null && resourceSpecialId != null){
            String errorCode = "permission.entry.error.only.need.one.resource.or.resourcespecial";

            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError("securedResource", errorCode);
            validationErrors.appendFieldError("securedResourceSpecial", errorCode);
            return false;
        }

        return false;
    }
}
