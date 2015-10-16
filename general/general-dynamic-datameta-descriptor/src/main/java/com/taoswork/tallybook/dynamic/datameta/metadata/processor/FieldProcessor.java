package com.taoswork.tallybook.dynamic.datameta.metadata.processor;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.BeginFieldMetadataFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.EndFieldMetadataFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.PresentationAnnotationFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics.BasicFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections.CollectionFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.embedded.EmbeddedFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence.PersistenceAnnotationFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldProcessor
    extends MultiMetadataHandler<Field, FieldMetadataIntermediate>
    implements IFieldHandler {

    public FieldProcessor(ClassProcessor classProcessor) {
        metaHandlers.add(new BeginFieldMetadataFieldHandler());

        metaHandlers.add(new PresentationAnnotationFieldHandler());
        metaHandlers.add(new EmbeddedFieldHandler(classProcessor));
        metaHandlers.add(new BasicFieldHandler());
        metaHandlers.add(new CollectionFieldHandler(classProcessor));
        metaHandlers.add(new PersistenceAnnotationFieldHandler());

        metaHandlers.add(new EndFieldMetadataFieldHandler());
    }
}
