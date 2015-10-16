package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persistence;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

public class PersistenceAnnotationFieldHandler
    extends MultiMetadataHandler<Field, FieldMetadataIntermediate>
    implements IFieldHandler {

    public PersistenceAnnotationFieldHandler() {
        metaHandlers.add(new _ColumnAnnotationHandler());
        metaHandlers.add(new _IdAnnotationHandler());
    }
}
