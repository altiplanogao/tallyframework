package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.classes.BasicClassHandler;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.classes.PresentationAnnotationClassHandler;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.classes.ProcessFieldsClassHandler;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields.IFieldHandler;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class ClassProcessor extends MultiMetadataHandler<Class<?>, ClassMetadata> {

    public ClassProcessor(IFieldHandler fieldHandler) {
        metaHandlers.add(new BasicClassHandler());
        metaHandlers.add(new PresentationAnnotationClassHandler());
        metaHandlers.add(new ProcessFieldsClassHandler(fieldHandler));
    }

}
