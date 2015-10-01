package com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.FieldValidatorBase;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.ValueValidationResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.extension.utils.AccountUtility;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class PhoneFieldValidator extends FieldValidatorBase<String> {
    @Override
    public FieldType supportedFieldType() {
        return FieldType.PHONE;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    public ValueValidationResult doValidate(FieldMetadata fieldMetadata, String fieldValue) {
        if (StringUtils.isBlank(fieldValue)) {
            return null;
        }
        boolean isEmail = AccountUtility.isMobile(fieldValue);
        if (!isEmail) {
            return new ValueValidationResult(false,
                "validation.error.email.format");
        }
        return null;
    }
}
