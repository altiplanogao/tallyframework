package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.EnumFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EnumFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
        if(presentationField != null &&
            FieldType.ENUMERATION.equals(presentationField.fieldType()) &&
            !(presentationField.enumeration().equals(Void.class))){

            EnumFieldMetaFacet enumFacet = new EnumFieldMetaFacet(presentationField.enumeration());
            fieldMetadata.addFacet(enumFacet);
            return ProcessResult.HANDLED;
        }else {
            return ProcessResult.INAPPLICABLE;
        }
    }
}
