package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.ArrayFieldFacet;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldHandler extends CollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayFieldHandler.class);

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadata fieldMetadata) {
        Class type = field.getType();
        if(Object[].class.isAssignableFrom(type)){
            Type genericType = field.getGenericType();
            if(!genericType.equals(type)){
                LOGGER.error("The List field should specify its parameter type.");
            }
            ArrayFieldFacet facet = new ArrayFieldFacet(genericType);

            fieldMetadata.addFacet(facet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
