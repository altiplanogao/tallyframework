package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _CollectionFieldHandler extends __BaseCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_CollectionFieldHandler.class);
    private final ClassProcessor classProcessor;

    public _CollectionFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class clazz = field.getType();
        if (Collection.class.isAssignableFrom(clazz)) {
            Type genericType = field.getGenericType();

            if (GenericTypeUtility.isTypeArgumentMissing(genericType)) {
                LOGGER.info("Field '{}.{}' having Type '{}' -> '{}' ",
                    field.getDeclaringClass().getSimpleName(), field.getName(),
                    clazz, genericType);
                LOGGER.warn("Field '{}.{}' having type '{}' should specify its type argument.",
                    field.getDeclaringClass().getSimpleName(), field.getName(), clazz);
            }

            Class elementType = Object.class;
            if (genericType instanceof ParameterizedType) {
                Type[] typeArgs = ((ParameterizedType) genericType).getActualTypeArguments();
                if (typeArgs != null && typeArgs.length > 0) {
                    if (typeArgs[0] instanceof Class) {
                        elementType = (Class) typeArgs[0];
                    } else {
                        elementType = Object.class;
                    }
                }
            } else {
                elementType = Object.class;
            }

            Class targetElementType = void.class;
            Class specifiedTarget = FieldMetadataHelper.getCollectionTargetType(field, true, true, true);
            if (specifiedTarget != null) {
                targetElementType = specifiedTarget;
            } else {
                targetElementType = elementType;
            }

            ClassMetadata embeddedCm = null;
            if (FieldMetadataHelper.isEmbeddable(targetElementType)) {
                embeddedCm = FieldMetadataHelper.generateEmbeddedClassMetadata(classProcessor, targetElementType);
            }

            CollectionFieldMetadataFacet facet = new CollectionFieldMetadataFacet(clazz, elementType, targetElementType, embeddedCm);
            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(CollectionFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
