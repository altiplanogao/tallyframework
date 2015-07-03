package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.ListFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ListFieldHandler extends CollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListFieldHandler.class);

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadata fieldMetadata) {
        Class clazz = field.getType();
        if(List.class.isAssignableFrom(clazz)){
            Type genericType = field.getGenericType();
            if(GenericTypeUtility.isTypeArgumentMissing(genericType)){
                LOGGER.info("Field '{}.{}' having Type '{}' -> '{}' ",
                        field.getDeclaringClass().getSimpleName(), field.getName(),
                        clazz, genericType);
                LOGGER.warn("Field '{}.{}' having type '{}' should specify its type argument.",
                        field.getDeclaringClass().getSimpleName(), field.getName(), clazz);
            }

            ListFieldFacet facet = new ListFieldFacet(genericType);
            fieldMetadata.addFacet(facet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}