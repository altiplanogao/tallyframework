package com.taoswork.tallybook.descriptor.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.MultiMetaHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.classes.*;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public abstract class GeneralClassProcessor
        extends MultiMetaHandler<Class<?>, MutableClassMeta>
        implements IClassProcessor {
    private final IFieldProcessor topFieldProcessor;

    public GeneralClassProcessor() {
        this.topFieldProcessor = createTopFieldProcessor();
        topFieldProcessor.setParentClassProcessor(this);
        topFieldProcessor.init();

        metaHandlers.add(new BeginClassMetaClassHandler());
        metaHandlers.add(new PersistableAnnotationClassHandler());
        metaHandlers.add(new PresentationAnnotationClassHandler());
        metaHandlers.add(new ProcessFieldsClassHandler(this));
        metaHandlers.add(new EndClassMetaClassHandler());
    }

    protected abstract IFieldProcessor createTopFieldProcessor();

    @Override
    public IFieldProcessor getTopFieldProcessor() {
        return topFieldProcessor;
    }

}
