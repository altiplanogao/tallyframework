package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationExternalForeignKey;

import java.lang.reflect.Field;

class _ExternalForeignKeyFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        try {
            PersistField persistField = field.getDeclaredAnnotation(PersistField.class);
            if (persistField != null && FieldType.EXTERNAL_FOREIGN_KEY.equals(persistField.fieldType())) {
                PresentationExternalForeignKey presentationExternalForeignKey = field.getDeclaredAnnotation(PresentationExternalForeignKey.class);
                if (presentationExternalForeignKey != null) {
                    Class hostClass = field.getDeclaringClass();

                    String dataField = presentationExternalForeignKey.dataField();
                    Field realDataField = hostClass.getDeclaredField(dataField);
                    Class declaredType = realDataField.getType();
                    Class targetType = presentationExternalForeignKey.targetType();

                    if (void.class.equals(targetType)) {
                        targetType = declaredType;
                    }
                    ExternalForeignEntityFieldMetadataFacet facet = new ExternalForeignEntityFieldMetadataFacet(dataField,
                        declaredType, targetType, presentationExternalForeignKey.idField(), presentationExternalForeignKey.displayField());
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
