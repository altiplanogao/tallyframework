package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.BasicFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import javax.persistence.Column;
import java.lang.reflect.Field;

class _ColumnAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        BasicFieldMetaFacet columnFacet = new BasicFieldMetaFacet();
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (null != columnAnnotation) {
            boolean nullable = columnAnnotation.nullable();
            fieldMetadata.setRequired(!nullable);

            int length = columnAnnotation.length();
            columnFacet.setLength(length);

            fieldMetadata.addFacet(columnFacet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
