package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.ArrayFieldMetadata;
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
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class type = field.getType();
        if (Object[].class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            if (!genericType.equals(type)) {
                LOGGER.error("The List field should specify its parameter type.");
            }
            Class elementType = type.getComponentType();
            ArrayFieldFacet facet = new ArrayFieldFacet(elementType);

            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(ArrayFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
