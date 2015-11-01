package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public abstract class TypedFieldHandlerBase<T> implements ITypedFieldHandler {

    protected boolean canHandle(IFieldMetadata fieldMetadata) {
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
