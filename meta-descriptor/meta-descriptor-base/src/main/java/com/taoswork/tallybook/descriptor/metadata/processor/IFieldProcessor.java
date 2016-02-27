package com.taoswork.tallybook.descriptor.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

/**
 * Created by Gao Yuan on 2015/2/22.
 */
public interface IFieldProcessor extends IFieldHandler {
    void setParentClassProcessor(IClassProcessor classProcessor);
    void init();
}
