package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
abstract class __BaseCollectionFieldHandler implements IFieldHandler{
    @Override
    public final ProcessResult process(Field a, FieldMetadata aMeta) {
        ProcessResult pr = processCollectionField(a, aMeta);
        if(pr.equals(ProcessResult.HANDLED)){
            aMeta.addFacet(new CollectionFieldMetaFacet());
        }
        return pr;
    }

    protected abstract ProcessResult processCollectionField(Field a, FieldMetadata aMeta);
}
