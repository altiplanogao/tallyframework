package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _EmbeddedFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        return ProcessResult.INAPPLICABLE;
    }
}
