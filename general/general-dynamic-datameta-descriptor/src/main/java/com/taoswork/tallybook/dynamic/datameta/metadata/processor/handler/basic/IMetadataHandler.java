package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;

public interface IMetadataHandler<NativeType, MetaType> {

    ProcessResult process(NativeType a, MetaType aMeta);
}
