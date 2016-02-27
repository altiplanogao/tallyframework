package com.taoswork.tallybook.descriptor.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public interface IClassProcessor extends IClassHandler {

    IFieldProcessor getTopFieldProcessor();
}
