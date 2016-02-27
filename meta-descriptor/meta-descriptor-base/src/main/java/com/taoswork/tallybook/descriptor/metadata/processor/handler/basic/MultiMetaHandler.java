package com.taoswork.tallybook.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public abstract class MultiMetaHandler<NativeType, MetaType>
        implements IMetaHandler<NativeType, MetaType> {
    protected final List<IMetaHandler<NativeType, MetaType>> metaHandlers;

    public MultiMetaHandler() {
        metaHandlers = new ArrayList<IMetaHandler<NativeType, MetaType>>();
    }

    @Override
    public final ProcessResult process(NativeType a, MetaType aMetadata) {
        int handled = 0;
        int failed = 0;
        for (IMetaHandler<NativeType, MetaType> handler : metaHandlers) {
            ProcessResult result = handler.process(a, aMetadata);
            switch (result) {
                case FAILED:
                    failed++;
                    break;
                case HANDLED:
                    handled++;
                    break;
            }
        }
        if (failed > 0) {
            return ProcessResult.FAILED;
        }
        if (handled > 0) {
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }

    public void add(IMetaHandler<NativeType, MetaType> handler){
        metaHandlers.add(handler);
    }
}
