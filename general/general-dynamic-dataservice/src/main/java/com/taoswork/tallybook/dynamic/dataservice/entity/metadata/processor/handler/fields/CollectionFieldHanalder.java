package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.CollectionFieldFacet;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
public abstract class CollectionFieldHanalder implements IFieldHandler{
    @Override
    public final ProcessResult process(Field a, FieldMetadata aMeta) {
        ProcessResult pr = processCollectionField(a, aMeta);
        if(pr.equals(ProcessResult.HANDLED)){
            aMeta.addFacet(new CollectionFieldFacet());
        }
        return pr;
    }

    protected abstract ProcessResult processCollectionField(Field a, FieldMetadata aMeta);
}
