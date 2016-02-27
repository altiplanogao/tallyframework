package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class _ArrayFieldHandler implements IFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_ArrayFieldHandler.class);

    public _ArrayFieldHandler() {
    }

    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        Class type = field.getType();
        if (Object[].class.isAssignableFrom(type)) {
            throw new IllegalAccessError("Array Type not supported");
        }
        return ProcessResult.INAPPLICABLE;
    }
}
