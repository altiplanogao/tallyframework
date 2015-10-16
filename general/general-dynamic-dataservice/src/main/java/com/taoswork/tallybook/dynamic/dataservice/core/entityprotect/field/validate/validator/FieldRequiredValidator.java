package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.FieldValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class FieldRequiredValidator extends FieldValidatorBase<String> {

    @Override
    public FieldType supportedFieldType() {
        return null;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    protected boolean nullValueAsValid() {
        return false;
    }

    @Override
    public ValidationError doValidate(IFieldMetadata fieldMetadata, String fieldValue) {
        if (fieldMetadata.isRequired() && StringUtils.isEmpty(fieldValue)) {
            return new ValidationError("validation.error.field.required");
        }
        return null;
    }
}
