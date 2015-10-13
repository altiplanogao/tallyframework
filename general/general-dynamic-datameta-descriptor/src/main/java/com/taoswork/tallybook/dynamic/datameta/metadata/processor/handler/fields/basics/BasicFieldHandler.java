package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics._BooleanFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics._EmbeddedFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics._EnumFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class BasicFieldHandler
        extends MultiMetadataHandler<Field, FieldMetadata>
        implements IFieldHandler {

    public BasicFieldHandler(){
        metaHandlers.add(new _EnumFieldHandler());
        metaHandlers.add(new _BooleanFieldHandler());
        metaHandlers.add(new _EmbeddedFieldHandler());
        metaHandlers.add(new _ForeignKeyFieldHandler());
    }
}
