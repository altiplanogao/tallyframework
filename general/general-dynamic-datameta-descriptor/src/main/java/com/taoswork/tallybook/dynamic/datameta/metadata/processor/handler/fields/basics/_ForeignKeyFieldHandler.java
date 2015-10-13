package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

class _ForeignKeyFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field a, FieldMetadata aMeta) {
        return ProcessResult.INAPPLICABLE;
    }
}
