package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.MultiMetaHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class BasicFieldHandler
        extends MultiMetaHandler<Field, FieldMetaMediate>
        implements IFieldHandler {

    public BasicFieldHandler() {
        metaHandlers.add(new _EnumFieldHandler());
        metaHandlers.add(new _BooleanFieldHandler());
        metaHandlers.add(new _DateFieldHandler());
        metaHandlers.add(new _ForeignKeyFieldHandler());
        metaHandlers.add(new _ExternalForeignKeyFieldHandler());
        metaHandlers.add(new _StringFieldHandler());
    }
}
