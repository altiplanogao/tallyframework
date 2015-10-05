package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class IdFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        Id idAnnoation = field.getDeclaredAnnotation(Id.class);
        if (null == idAnnoation) {
            fieldMetadata.setId(false);
            return ProcessResult.INAPPLICABLE;
        } else {
            fieldMetadata.setId(true);
            PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
            if (presentationField == null) {
                fieldMetadata.setOrder(0);
                fieldMetadata.setVisibility(Visibility.HIDDEN_ALL);
            }
            return ProcessResult.HANDLED;
        }
    }
}
