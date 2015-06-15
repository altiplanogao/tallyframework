package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.basic;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public abstract class MultiMetadataHandler<NativeType, MetaType> implements IMetadataHandler<NativeType, MetaType> {
    protected final List<IMetadataHandler<NativeType, MetaType>> metaHandlers;

    public MultiMetadataHandler(){
        metaHandlers = new ArrayList<IMetadataHandler<NativeType, MetaType>>();
    }

    @Override
    public final ProcessResult process(NativeType a, MetaType aMetadata) {
        int handled = 0;
        int failed = 0;
        for(IMetadataHandler<NativeType, MetaType> handler : metaHandlers){
            ProcessResult result = handler.process(a, aMetadata);
            switch (result){
                case FAILED:
                    failed++;
                    break;
                case HANDLED:
                    handled++;
                    break;
            }
        }
        if(failed > 0){
            return ProcessResult.FAILED;
        }
        if(handled > 0){
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
