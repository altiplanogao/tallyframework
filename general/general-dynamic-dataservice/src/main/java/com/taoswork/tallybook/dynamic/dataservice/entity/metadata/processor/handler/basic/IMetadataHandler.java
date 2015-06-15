package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.basic;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface IMetadataHandler<NativeType, MetaType> {
    ProcessResult process(NativeType a, MetaType aMeta);
}
