package com.taoswork.tallybook.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;
import com.taoswork.tallybook.descriptor.metadata.utils.FriendlyNameHelper;

public class BeginClassMetaClassHandler implements IClassHandler {
    @Override
    public ProcessResult process(Class clz, MutableClassMeta mutableClassMetadata) {
        mutableClassMetadata.setName(clz.getName());
        mutableClassMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Class(clz));

        return ProcessResult.PASSING_THROUGH;
    }
}
