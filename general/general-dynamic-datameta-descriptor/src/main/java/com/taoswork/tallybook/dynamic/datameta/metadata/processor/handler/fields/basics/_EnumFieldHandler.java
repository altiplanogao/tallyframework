package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EnumFieldMetaFacet;
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
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
        if(presentationField != null &&
            FieldType.ENUMERATION.equals(presentationField.fieldType())){
            EnumField enumField = field.getDeclaredAnnotation(EnumField.class);
            if(!enumField.enumeration().equals(Void.class)){
                EnumFieldMetaFacet enumFacet = new EnumFieldMetaFacet(enumField.enumeration());
                fieldMetadata.addFacet(enumFacet);
                return ProcessResult.HANDLED;
            }
            return ProcessResult.INAPPLICABLE;
        }else {
            return ProcessResult.INAPPLICABLE;
        }
    }
}
