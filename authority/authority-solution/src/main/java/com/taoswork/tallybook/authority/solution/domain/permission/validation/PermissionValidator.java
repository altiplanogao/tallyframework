package com.taoswork.tallybook.authority.solution.domain.permission.validation;

import com.taoswork.tallybook.authority.solution.domain.permission.Permission;
import com.taoswork.tallybook.datadomain.base.entity.validation.BaseEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2016/2/7.
 */
public class PermissionValidator
        extends BaseEntityValidator<Permission> {
    @Override
    protected boolean doValidate(Permission entity, EntityValidationErrors validationErrors) {
        String resourceEntity = entity.getResource();
        if (StringUtils.isEmpty(resourceEntity)) {
            String errorCode = "permission.error.need.resource";
            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError("resource", errorCode);
            return false;
        }
        return true;
    }
}
