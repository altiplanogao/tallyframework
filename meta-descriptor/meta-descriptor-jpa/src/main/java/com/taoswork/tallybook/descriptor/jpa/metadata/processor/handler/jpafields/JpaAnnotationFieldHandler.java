package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.MultiMetaHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

public class JpaAnnotationFieldHandler
        extends MultiMetaHandler<Field, FieldMetaMediate>
        implements IFieldHandler {

    public JpaAnnotationFieldHandler() {
        metaHandlers.add(new _IdAnnotationHandler());
        metaHandlers.add(new _VersionFieldHandler());
        metaHandlers.add(new _ColumnAnnotationHandler());
        metaHandlers.add(new _ManyToOneAnnotationHandler());
        metaHandlers.add(new _OneToOneAnnotationHandler());
        metaHandlers.add(new _ElementCollectionAnnotationHandler());
        metaHandlers.add(new _OneToManyAnnotationHandler());
        metaHandlers.add(new _ManyToManyAnnotationHandler());
        metaHandlers.add(new _EmbeddedAnnotationHandler());
    }
}
