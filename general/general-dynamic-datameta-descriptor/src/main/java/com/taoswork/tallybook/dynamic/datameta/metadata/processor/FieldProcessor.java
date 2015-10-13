package com.taoswork.tallybook.dynamic.datameta.metadata.processor;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics.BasicFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections.CollectionFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence.PersistenceAnnotationFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldProcessor
        extends MultiMetadataHandler<Field, FieldMetadata>
        implements IFieldHandler{

    public FieldProcessor(){
        metaHandlers.add(new BeginFieldMetadataFieldHandler());

        metaHandlers.add(new PresentationAnnotationFieldHandler());
        metaHandlers.add(new BasicFieldHandler());
        metaHandlers.add(new CollectionFieldHandler());
        metaHandlers.add(new PersistenceAnnotationFieldHandler());

        metaHandlers.add(new EndFieldMetadataFieldHandler());
    }
}
