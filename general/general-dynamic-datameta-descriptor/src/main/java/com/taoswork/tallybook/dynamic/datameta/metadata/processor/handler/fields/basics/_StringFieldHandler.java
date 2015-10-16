package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.StringFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _StringFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        if (String.class.equals(field.getType())) {
            if (fieldMetadata.getTargetMetadataType() == null) {
                fieldMetadata.setTargetMetadataType(StringFieldMetadata.class);
                return ProcessResult.PASSING_THROUGH;
            }
            return ProcessResult.INAPPLICABLE;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
