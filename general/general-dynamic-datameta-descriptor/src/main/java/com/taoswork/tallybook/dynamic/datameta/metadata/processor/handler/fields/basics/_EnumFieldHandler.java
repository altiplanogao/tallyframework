package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EnumFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.EnumFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationEnum;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _EnumFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        PersistField persistField = field.getDeclaredAnnotation(PersistField.class);
        if (persistField != null &&
            FieldType.ENUMERATION.equals(persistField.fieldType())) {
            PresentationEnum presentationEnum = field.getDeclaredAnnotation(PresentationEnum.class);
            if (!presentationEnum.enumeration().equals(void.class)) {
                EnumFieldMetadataFacet enumFacet = new EnumFieldMetadataFacet(presentationEnum.enumeration());
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
