package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.StringFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.FieldValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class FieldLengthValidator extends FieldValidatorBase<String> {
    @Override
    public FieldType supportedFieldType() {
        return null;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    public ValidationError doValidate(IFieldMetadata fieldMetadata, String fieldValue) {
        if (fieldMetadata instanceof StringFieldMetadata) {
            int maxLength = ((StringFieldMetadata) fieldMetadata).getLength();
            int length = fieldValue.length();
            if (length > maxLength) {
                return new ValidationError("validation.error.field.length",
                    new Object[]{maxLength, length});
            }
        }
        return null;
    }
}
