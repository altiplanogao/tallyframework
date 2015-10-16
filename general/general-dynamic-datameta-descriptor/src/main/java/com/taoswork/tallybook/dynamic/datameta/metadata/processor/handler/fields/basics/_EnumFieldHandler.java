package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EnumFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.EnumFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.EnumField;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _EnumFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
        if (presentationField != null &&
            FieldType.ENUMERATION.equals(presentationField.fieldType())) {
            EnumField enumField = field.getDeclaredAnnotation(EnumField.class);
            if (!enumField.enumeration().equals(void.class)) {
                EnumFieldFacet enumFacet = new EnumFieldFacet(enumField.enumeration());
                fieldMetadata.addFacet(enumFacet);
                fieldMetadata.setTargetMetadataType(EnumFieldMetadata.class);
                return ProcessResult.HANDLED;
            }
            return ProcessResult.INAPPLICABLE;
        } else {
            return ProcessResult.INAPPLICABLE;
        }
    }
}
