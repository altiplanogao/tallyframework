package com.taoswork.tallybook.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public abstract class BaseFieldHandler
        extends BaseMetaHandler<Field, FieldMetaMediate>
        implements IFieldHandler {
    protected abstract boolean canProcess(Field field, FieldMetaMediate metaMediate);

    protected abstract ProcessResult doProcess(Field field, FieldMetaMediate metaMediate);
}
