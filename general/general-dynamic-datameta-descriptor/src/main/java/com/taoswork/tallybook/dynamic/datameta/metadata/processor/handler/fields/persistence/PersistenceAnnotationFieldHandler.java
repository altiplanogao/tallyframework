package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

public class PersistenceAnnotationFieldHandler
        extends MultiMetadataHandler<Field, FieldMetadata>
        implements IFieldHandler {

    public PersistenceAnnotationFieldHandler(){
        metaHandlers.add(new _ColumnAnnotationHandler());
        metaHandlers.add(new _IdAnnotationHandler());
    }
}
