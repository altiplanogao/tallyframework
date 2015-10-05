package com.taoswork.tallybook.dynamic.dataservice.core.field.handler;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public abstract class FieldTypedHandlerBase<T> implements IFieldTypedHandler {

    protected boolean canHandle(FieldMetadata fieldMetadata) {
        Class fieldClass = fieldMetadata.getFieldClass();
        FieldType fieldType = fieldMetadata.getFieldType();

        Class supportedClass = this.supportedFieldClass();
        FieldType supportedType = this.supportedFieldType();

        if (supportedType != null) {
            if (!supportedType.equals(fieldType)) {
                return false;
            }
        }

        if (supportedClass != null) {
            if (!supportedClass.equals(fieldClass)) {
                return false;
            }
        }

        return true;
    }
}
