package com.taoswork.tallybook.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.validate.BaseTypedFieldValidator;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2015/10/26.
 */
public class ForeignKeyFieldValidator extends BaseTypedFieldValidator<Persistable> {
    @Override
    public FieldType supportedFieldType() {
        return FieldType.FOREIGN_KEY;
    }

    @Override
    public Class supportedFieldClass() {
        return null;
    }

    @Override
    public ValidationError doValidate(IFieldMeta fieldMeta, Persistable fieldValue) {
        if (fieldMeta.isRequired() && (null == fieldValue)) {
            return new ValidationError("validation.error.field.required");
        }
        return null;
    }
}
