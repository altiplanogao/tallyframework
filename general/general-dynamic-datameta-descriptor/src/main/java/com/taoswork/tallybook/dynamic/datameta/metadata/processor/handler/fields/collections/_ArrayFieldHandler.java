package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.ArrayFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _ArrayFieldHandler extends _1DCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_ArrayFieldHandler.class);
    private final ClassProcessor classProcessor;

    public _ArrayFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class type = field.getType();
        if (Object[].class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            if (!genericType.equals(type)) {
                LOGGER.error("The List field should specify its parameter type.");
            }
            Class entryType = type.getComponentType();

            ClassMetadata embeddedCm = null;
            if (FieldMetadataHelper.isEmbeddable(entryType)) {
                embeddedCm = FieldMetadataHelper.generateEmbeddedClassMetadata(classProcessor, entryType);
            }
            ArrayFieldMetadataFacet facet = new ArrayFieldMetadataFacet(entryType, embeddedCm);

            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(ArrayFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
