package com.taoswork.tallybook.dynamic.datameta.metadata.processor;

import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.MutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes.*;

public class ClassProcessor extends MultiMetadataHandler<Class<?>, MutableClassMetadata> implements IClassHandler {

    public ClassProcessor() {
        metaHandlers.add(new BeginClassMetadataClassHandler());
        metaHandlers.add(new PersistableAnnotationClassHandler());
        metaHandlers.add(new PresentationAnnotationClassHandler());
        metaHandlers.add(new ProcessFieldsClassHandler(this));
        metaHandlers.add(new EndClassMetadataClassHandler());
    }
}
