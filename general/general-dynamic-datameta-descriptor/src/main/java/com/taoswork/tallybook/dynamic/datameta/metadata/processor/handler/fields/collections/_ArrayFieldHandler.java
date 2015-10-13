package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _ArrayFieldHandler extends __BaseCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_ArrayFieldHandler.class);

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadata fieldMetadata) {
        Class type = field.getType();
        if(Object[].class.isAssignableFrom(type)){
            Type genericType = field.getGenericType();
            if(!genericType.equals(type)){
                LOGGER.error("The List field should specify its parameter type.");
            }
            ArrayFieldMetaFacet facet = new ArrayFieldMetaFacet(genericType);

            fieldMetadata.addFacet(facet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
