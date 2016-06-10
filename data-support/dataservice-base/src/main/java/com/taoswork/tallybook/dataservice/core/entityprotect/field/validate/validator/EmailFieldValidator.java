package com.taoswork.tallybook.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.validate.BaseTypedFieldValidator;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.general.extension.utils.AccountUtility;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EmailFieldValidator extends BaseTypedFieldValidator<String> {
    @Override
    public FieldType supportedFieldType() {
        return FieldType.EMAIL;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    public ValidationError doValidate(IFieldMeta fieldMeta, String fieldValue) {
        if (StringUtils.isBlank(fieldValue)) {
            return null;
        }
        boolean isEmail = AccountUtility.isEmail(fieldValue);
        if (!isEmail) {
            return new ValidationError("validation.error.email.format");
        }
        return null;
    }
}
