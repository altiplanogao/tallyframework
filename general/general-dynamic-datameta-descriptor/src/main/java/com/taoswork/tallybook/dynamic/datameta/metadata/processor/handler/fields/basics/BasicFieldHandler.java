package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class BasicFieldHandler
    extends MultiMetadataHandler<Field, FieldMetadataIntermediate>
    implements IFieldHandler {

    public BasicFieldHandler() {
        metaHandlers.add(new _EnumFieldHandler());
        metaHandlers.add(new _BooleanFieldHandler());
        metaHandlers.add(new _ForeignKeyFieldHandler());
        metaHandlers.add(new _ExternalForeignKeyFieldHandler());
        metaHandlers.add(new _StringFieldHandler());
        metaHandlers.add(new _PaleFieldHandler());
    }
}
