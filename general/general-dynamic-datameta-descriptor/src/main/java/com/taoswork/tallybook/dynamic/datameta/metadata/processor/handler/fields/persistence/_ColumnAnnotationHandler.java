package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.StringFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import javax.persistence.Column;
import java.lang.reflect.Field;

class _ColumnAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        BasicFieldMetadataObject bfmo = fieldMetadata.getBasicFieldMetadataObject();

        StringFieldMetadataFacet columnFacet = new StringFieldMetadataFacet();
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (null != columnAnnotation) {
            boolean nullable = columnAnnotation.nullable();
            bfmo.setRequired(!nullable);

            int length = columnAnnotation.length();
            columnFacet.setLength(length);

            fieldMetadata.addFacet(columnFacet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
