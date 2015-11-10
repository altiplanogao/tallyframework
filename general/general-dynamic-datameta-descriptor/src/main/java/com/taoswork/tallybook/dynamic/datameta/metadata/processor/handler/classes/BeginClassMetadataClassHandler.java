package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.MutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;

public class BeginClassMetadataClassHandler implements IClassHandler {
    @Override
    public ProcessResult process(Class clz, MutableClassMetadata mutableClassMetadata) {
        mutableClassMetadata.setName(clz.getName());
        mutableClassMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Class(clz));

        return ProcessResult.PASSING_THROUGH;
    }
}
