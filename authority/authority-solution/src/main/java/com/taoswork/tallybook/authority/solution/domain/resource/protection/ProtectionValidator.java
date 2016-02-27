package com.taoswork.tallybook.authority.solution.domain.resource.protection;

import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.domain.resource.ProtectionCase;
import com.taoswork.tallybook.datadomain.base.entity.validation.BaseEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ProtectionValidator extends BaseEntityValidator<Protection> {
    @Override
    protected boolean doValidate(Protection entity, EntityValidationErrors validationErrors) {
        boolean ok = true;
        if(StringUtils.isBlank(entity.getResource())){
            String errorCode = "Protection.reqires.resource.type";
            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError("resource", errorCode);
            ok = false;
        }
        if(StringUtils.isBlank(entity.getProtectionSpace())){
            String errorCode = "Protection.reqires.protection.scope";
            validationErrors.appendError(errorCode);
            validationErrors.appendFieldError(Protection.FN_PROTECTION_SPACE, errorCode);
            ok = false;
        }
        Map<String, ProtectionCase> cases = entity.getCases();
        if(cases != null){
            for (Map.Entry<String, ProtectionCase> entry : cases.entrySet()){
                ProtectionCase _case = entry.getValue();
                String uniqueCode = entry.getKey();
                if (!uniqueCode.equals(_case.getUuid())){
                    String errorCode = "internal.error";
                    validationErrors.appendError(errorCode);
                    validationErrors.appendFieldError("cases." + uniqueCode, errorCode);
                    ok = false;
                }
            }
        }
        return ok;
    }
}
