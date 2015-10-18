package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import java.lang.reflect.Field;

public class PresentationAnnotationFieldHandler implements IFieldHandler {

    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);

        BasicFieldMetadataObject bfmo = fieldMetadata.getBasicFieldMetadataObject();

        bfmo.setTabName(PresentationClass.Tab.DEFAULT_NAME);
        bfmo.setGroupName(PresentationClass.Group.DEFAULT_NAME);
        bfmo.setOrder(PresentationField.DEFAULT_ORDER_BIAS + bfmo.getOriginalOrder());
        bfmo.setVisibility(Visibility.DEFAULT);
        bfmo.setFieldType(FieldType.UNKNOWN);
        bfmo.setNameField(false);

        if (presentationField != null) {
            bfmo.setTabName(presentationField.tab());
            bfmo.setGroupName(presentationField.group());
            if (presentationField.order() != PresentationField.ORDER_NOT_DEFINED) {
                bfmo.setOrder(presentationField.order());
            }
            bfmo.setVisibility(presentationField.visibility());
            bfmo.setFieldType(presentationField.fieldType());
            bfmo.setNameField(presentationField.nameField());
            if (presentationField.required()) {
                bfmo.setRequired(true);
            }
        }
        return ProcessResult.HANDLED;
    }
}