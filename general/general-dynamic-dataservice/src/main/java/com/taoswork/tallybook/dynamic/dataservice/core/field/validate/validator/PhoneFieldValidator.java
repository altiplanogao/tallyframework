package com.taoswork.tallybook.dynamic.dataservice.core.field.validate.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.FieldValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
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
    public ValidationError doValidate(FieldMetadata fieldMetadata, String fieldValue) {
        if (StringUtils.isBlank(fieldValue)) {
            return null;
        }
        boolean isEmail = AccountUtility.isPhone(fieldValue);
        if (!isEmail) {
            return new ValidationError("validation.error.phone.format", new Object[]{AccountUtility.VALID_PHONE_NUMBERS_STRING});
        }
        return null;
    }
}