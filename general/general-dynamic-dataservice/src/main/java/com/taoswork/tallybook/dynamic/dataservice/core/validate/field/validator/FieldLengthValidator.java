package com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.BasicFieldMetaFacet;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.FieldValidatorBase;
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
    public ValidationError doValidate(FieldMetadata fieldMetadata, String fieldValue) {
        BasicFieldMetaFacet basicFieldMetaFacet = (BasicFieldMetaFacet) fieldMetadata.getFacet(FieldFacetType.Basic);
        if (basicFieldMetaFacet != null) {
            int maxLength = basicFieldMetaFacet.getLength();
            int length = fieldValue.length();
            if (length > maxLength) {
                return new ValidationError("validation.error.field.length",
                    new Object[]{maxLength, length});
            }
        }
        return null;
    }
}
