package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;

public class BeginClassMetadataClassHandler implements IClassHandler {
    @Override
    public ProcessResult process(Class clz, ClassMetadata classMetadata) {
        classMetadata.setEntityClz(clz);
        classMetadata.setName(clz.getName());
        classMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Class(clz));

        return ProcessResult.PASSING_THROUGH;
    }
}
