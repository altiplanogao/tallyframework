package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

class _ForeignKeyFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class targetType = FieldMetadataHelper.getToOneTargetType(field, true, true);
        if (targetType != null) {
            Class fieldType = field.getType();
            ForeignEntityFieldMetadataFacet facet = new ForeignEntityFieldMetadataFacet(fieldType, targetType);
            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(ForeignEntityFieldMetadata.class);
            return ProcessResult.HANDLED;
        }

        return ProcessResult.INAPPLICABLE;
    }
}
