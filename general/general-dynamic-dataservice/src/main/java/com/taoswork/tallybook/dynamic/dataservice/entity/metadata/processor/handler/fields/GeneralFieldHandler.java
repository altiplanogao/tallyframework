package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class GeneralFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        return ProcessResult.INAPPLICABLE;
    }
}
