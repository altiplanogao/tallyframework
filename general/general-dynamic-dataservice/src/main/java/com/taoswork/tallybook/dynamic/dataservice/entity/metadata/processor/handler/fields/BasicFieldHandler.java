package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationClass;
import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationField;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.FriendlyNameHelper;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class BasicFieldHandler implements IFieldHandler {
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
        fieldMetadata.setNameField(false);

        if(presentationField != null){
            fieldMetadata.setTabName(presentationField.tab());
            fieldMetadata.setGroupName(presentationField.group());
            if(presentationField.order() != PresentationField.ORDER_NOT_DEFINED){
                fieldMetadata.setOrder(presentationField.order());
            }
            fieldMetadata.setVisibility(presentationField.visibility());
            fieldMetadata.setFieldType(presentationField.fieldType());
            fieldMetadata.setNameField(presentationField.nameField());
        }
        return ProcessResult.HANDLED;
    }
}
