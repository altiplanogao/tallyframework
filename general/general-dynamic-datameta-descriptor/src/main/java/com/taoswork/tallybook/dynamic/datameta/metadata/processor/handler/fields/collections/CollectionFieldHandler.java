package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

public class CollectionFieldHandler
    extends MultiMetadataHandler<Field, FieldMetadataIntermediate>
    implements IFieldHandler {

    public CollectionFieldHandler(ClassProcessor classProcessor) {
        metaHandlers.add(new _ArrayFieldHandler());
        metaHandlers.add(new _MapFieldHandler(classProcessor));
        metaHandlers.add(new _CollectionFieldHandler(classProcessor));
    }
}
