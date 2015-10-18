package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;

import java.lang.reflect.Field;

public class BeginFieldMetadataFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        BasicFieldMetadataObject bfmo = fieldMetadata.getBasicFieldMetadataObject();
//        bfmo.setField(field);
        bfmo.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Field(field));

        return ProcessResult.PASSING_THROUGH;
    }
}
