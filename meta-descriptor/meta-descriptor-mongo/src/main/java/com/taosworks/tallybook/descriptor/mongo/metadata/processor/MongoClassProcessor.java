package com.taosworks.tallybook.descriptor.mongo.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.processor.GeneralClassProcessor;
import com.taoswork.tallybook.descriptor.metadata.processor.IFieldProcessor;

public class MongoClassProcessor
        extends GeneralClassProcessor {

    @Override
    protected IFieldProcessor createTopFieldProcessor() {
        return new MongoFieldProcessor();
    }
}
