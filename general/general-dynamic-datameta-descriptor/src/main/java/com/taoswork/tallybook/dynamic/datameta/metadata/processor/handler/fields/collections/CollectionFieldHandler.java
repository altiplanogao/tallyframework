package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections._ArrayFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections._ListFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections._MapFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections._SetFieldHandler;

import java.lang.reflect.Field;

public class CollectionFieldHandler
        extends MultiMetadataHandler<Field, FieldMetadata>
        implements IFieldHandler {

    public CollectionFieldHandler(){
        metaHandlers.add(new _ArrayFieldHandler());
        metaHandlers.add(new _ListFieldHandler());
        metaHandlers.add(new _SetFieldHandler());
        metaHandlers.add(new _MapFieldHandler());
    }
}
