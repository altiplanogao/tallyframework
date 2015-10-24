package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationForeignKey;

import java.lang.reflect.Field;

class _ForeignKeyFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class targetType = FieldMetadataHelper.getToOneTargetType(field, true, true);
        if (targetType != null) {
            PresentationForeignKey presentationForeignKey = field.getDeclaredAnnotation(PresentationForeignKey.class);
            String nameField = "name";
            if(presentationForeignKey != null){
                nameField = presentationForeignKey.displayField();
            }
            Class fieldType = field.getType();
            ForeignEntityFieldMetadataFacet facet = new ForeignEntityFieldMetadataFacet(fieldType, targetType, nameField);
            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(ForeignEntityFieldMetadata.class);
            return ProcessResult.HANDLED;
        }

        return ProcessResult.INAPPLICABLE;
    }
}
