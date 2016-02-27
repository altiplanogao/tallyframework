package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.Column;
import java.lang.reflect.Field;

class _ColumnAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();

        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (null != columnAnnotation) {
            boolean nullable = columnAnnotation.nullable();
            boolean required = bfmo.isRequired();
            if (nullable == required) {
                throw new MetadataException("Wrong configuration");
//                return ProcessResult.FAILED;
            }

            int length = columnAnnotation.length();
            if(String.class.equals(field.getType())) {
                if (length < bfmo.getLength()) {
                    throw new MetadataException("Wrong configuration");
//                return ProcessResult.FAILED;
                }
            }

            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
