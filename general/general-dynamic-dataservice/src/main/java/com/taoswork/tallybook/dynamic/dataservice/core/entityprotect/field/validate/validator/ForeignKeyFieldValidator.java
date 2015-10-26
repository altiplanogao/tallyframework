package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.FieldValidatorBase;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.extension.utils.AccountUtility;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/10/26.
 */
public class ForeignKeyFieldValidator extends FieldValidatorBase<Persistable> {
    @Override
    public FieldType supportedFieldType() {
        return FieldType.FOREIGN_KEY;
    }

    @Override
    public Class supportedFieldClass() {
        return null;
    }

    @Override
    public ValidationError doValidate(IFieldMetadata fieldMetadata, Persistable fieldValue) {
        if (fieldMetadata.isRequired() && (null == fieldValue)) {
            return new ValidationError("validation.error.field.required");
        }
        return null;
    }
}
