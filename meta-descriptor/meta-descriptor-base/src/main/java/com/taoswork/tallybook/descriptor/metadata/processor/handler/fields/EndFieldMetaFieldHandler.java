package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

public class EndFieldMetaFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {

        return ProcessResult.PASSING_THROUGH;
    }
}
