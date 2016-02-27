package com.taoswork.tallybook.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public abstract class BaseCollectionFieldHandlerDispatcher
        extends BaseFieldHandler {

    protected final List<IFieldHandler> fieldHandlerList = new ArrayList<IFieldHandler>();

    public BaseCollectionFieldHandlerDispatcher() {
    }

    protected boolean allowUnhandled(){
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        IFieldMetaSeed seed = metaMediate.getMetaSeed();
        if (seed != null) {
            throw new MetadataException("Seed already created, unexpected");
        }

        for (IFieldHandler handler : fieldHandlerList) {
            ProcessResult pr = handler.process(field, metaMediate);
            seed = metaMediate.getMetaSeed();
            if (ProcessResult.HANDLED.equals(pr)) {
                if (seed == null) {
                    throw new MetadataException("Process Handled, but seed not set, Unexpected");
                }
                return ProcessResult.HANDLED;
            } else {
                if (seed != null) {
                    throw new MetadataException("Process not Handled, but seed set, Unexpected");
                }
            }
        }

        if(!allowUnhandled()){
            MetadataException.throwFieldConfigurationException(field, "Collection field not handled.");
        }

        return ProcessResult.INAPPLICABLE;
    }
}
