package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Id;
import java.lang.reflect.Field;

class _IdAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        BasicFieldMetadataObject bfmo = fieldMetadata.getBasicFieldMetadataObject();
        Id idAnnotation = field.getDeclaredAnnotation(Id.class);
        if (null != idAnnotation) {
            bfmo.setId(true);
            PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
            if (presentationField == null) {
                bfmo.setOrder(0);
                bfmo.setVisibility(Visibility.HIDDEN_ALL);
            }
            return ProcessResult.HANDLED;
        } else {
            bfmo.setId(false);
            return ProcessResult.INAPPLICABLE;
        }
    }
}
