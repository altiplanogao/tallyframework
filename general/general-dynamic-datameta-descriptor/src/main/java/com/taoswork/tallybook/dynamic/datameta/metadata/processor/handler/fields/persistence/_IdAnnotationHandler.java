package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Id;
import java.lang.reflect.Field;

class _IdAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        Id idAnnotation = field.getDeclaredAnnotation(Id.class);
        if (null != idAnnotation) {
            fieldMetadata.setId(true);
            PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
            if (presentationField == null) {
                fieldMetadata.setOrder(0);
                fieldMetadata.setVisibility(Visibility.HIDDEN_ALL);
            }
            return ProcessResult.HANDLED;
        } else {
            fieldMetadata.setId(false);
            return ProcessResult.INAPPLICABLE;
        }
    }
}
