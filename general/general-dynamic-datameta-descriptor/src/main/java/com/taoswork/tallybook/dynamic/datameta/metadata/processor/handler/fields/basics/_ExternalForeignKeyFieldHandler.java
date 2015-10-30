package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationExternalForeignKey;

import java.lang.reflect.Field;

class _ExternalForeignKeyFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        try {
            PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
            if (presentationField != null && FieldType.EXTERNAL_FOREIGN_KEY.equals(presentationField.fieldType())) {
                PresentationExternalForeignKey presentationExternalForeignKey = field.getDeclaredAnnotation(PresentationExternalForeignKey.class);
                if (presentationExternalForeignKey != null) {
                    String targetField = presentationExternalForeignKey.targetField();
                    Class targetType = presentationExternalForeignKey.targetType();

                    Class hostClass = field.getDeclaringClass();
                    Field realTargetField = hostClass.getDeclaredField(targetField);
                    if (void.class.equals(targetType)) {
                        targetType = field.getType();
                    }
                    ExternalForeignEntityFieldMetadataFacet facet = new ExternalForeignEntityFieldMetadataFacet(targetField,
                        targetType, presentationExternalForeignKey.idField(), presentationExternalForeignKey.displayField());
                    fieldMetadata.addFacet(facet);
                    fieldMetadata.setTargetMetadataType(ExternalForeignEntityFieldMetadata.class);
                    return ProcessResult.HANDLED;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return ProcessResult.INAPPLICABLE;
        }

        return ProcessResult.INAPPLICABLE;
    }
}
