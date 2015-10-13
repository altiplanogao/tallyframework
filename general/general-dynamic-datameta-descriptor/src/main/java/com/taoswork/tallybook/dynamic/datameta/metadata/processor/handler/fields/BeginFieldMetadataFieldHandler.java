package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;

import java.lang.reflect.Field;

public class BeginFieldMetadataFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        fieldMetadata.setField(field);
        fieldMetadata.setName(field.getName());
        fieldMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Field(field));
        fieldMetadata.setFieldClass(field.getType());

        return ProcessResult.PASSING_THROUGH;
    }
}
