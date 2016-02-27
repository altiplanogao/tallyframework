package com.taoswork.tallybook.descriptor.jpa.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.processor.GeneralClassProcessor;
import com.taoswork.tallybook.descriptor.metadata.processor.IFieldProcessor;

public class JpaClassProcessor
        extends GeneralClassProcessor {

    @Override
    protected IFieldProcessor createTopFieldProcessor() {
        return new JpaFieldProcessor();
    }
}
