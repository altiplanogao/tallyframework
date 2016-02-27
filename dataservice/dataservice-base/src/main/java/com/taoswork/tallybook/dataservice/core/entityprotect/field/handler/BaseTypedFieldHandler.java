package com.taoswork.tallybook.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

public abstract class BaseTypedFieldHandler<T> implements ITypedFieldHandler {

    protected boolean canHandle(IFieldMeta fieldMeta) {
        Class fieldClass = fieldMeta.getFieldClass();
        FieldType fieldType = fieldMeta.getFieldType();

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
