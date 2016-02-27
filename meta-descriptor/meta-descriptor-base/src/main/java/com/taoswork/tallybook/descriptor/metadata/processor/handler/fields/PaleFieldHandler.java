package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.PaleFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class PaleFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        if (metaMediate.noSeed()) {
            metaMediate.setMetaSeed(new PaleFieldMeta.Seed());
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
