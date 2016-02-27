package com.taoswork.tallybook.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;

public interface IMetaHandler<NativeType, MetaType> {

    ProcessResult process(NativeType a, MetaType aMeta);
}
