package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;

import java.lang.reflect.Field;

public class EndFieldMetadataFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {

        return ProcessResult.PASSING_THROUGH;
    }
}
