package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class GeneralFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        fieldMetadata.setField(field);
        fieldMetadata.setName(field.getName());
        fieldMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Field(field));
        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);

        fieldMetadata.setTabName(PresentationClass.Tab.DEFAULT_NAME);
        fieldMetadata.setGroupName(PresentationClass.Group.DEFAULT_NAME);
        fieldMetadata.setOrder(PresentationField.DEFAULT_ORDER_BIAS + fieldMetadata.getOriginalOrder());
        fieldMetadata.setVisibility(Visibility.DEFAULT);
        fieldMetadata.setFieldType(FieldType.UNKNOWN);
        fieldMetadata.setNameField(false);
        fieldMetadata.setFieldClass(field.getType());

        if(presentationField != null){
            fieldMetadata.setTabName(presentationField.tab());
            fieldMetadata.setGroupName(presentationField.group());
            if(presentationField.order() != PresentationField.ORDER_NOT_DEFINED){
                fieldMetadata.setOrder(presentationField.order());
            }
            fieldMetadata.setVisibility(presentationField.visibility());
            fieldMetadata.setFieldType(presentationField.fieldType());
            fieldMetadata.setNameField(presentationField.nameField());
            if(presentationField.required()){
                fieldMetadata.setRequired(true);
            }
        }
        return ProcessResult.HANDLED;
    }
}
