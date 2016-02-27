package com.taoswork.tallybook.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public abstract class BaseMetaHandler<NativeType, MetaType>
        implements IMetaHandler<NativeType, MetaType> {

    protected abstract boolean canProcess(NativeType a, MetaType aMeta);

    protected abstract ProcessResult doProcess(NativeType a, MetaType aMeta);

    @Override
    public final ProcessResult process(NativeType a, MetaType aMeta) {
        if(canProcess(a, aMeta)){
            ProcessResult pr = doProcess(a, aMeta);
            return pr;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
