package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.MapFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _MapFieldHandler extends __BaseCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_MapFieldHandler.class);
    private final ClassProcessor classProcessor;

    public _MapFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class clazz = field.getType();
        if (Map.class.isAssignableFrom(clazz)) {
            Type genericType = field.getGenericType();
            if (GenericTypeUtility.isTypeArgumentMissing(genericType)) {
                LOGGER.info("Field '{}.{}' having Type '{}' -> '{}' ",
                    field.getDeclaringClass().getSimpleName(), field.getName(),
                    clazz, genericType);
                LOGGER.warn("Field '{}.{}' having type '{}' should specify its type argument.",
                    field.getDeclaringClass().getSimpleName(), field.getName(), clazz);
            }

            Class keyType = Object.class;
            Class valueType = Object.class;
            if (genericType instanceof ParameterizedType) {
                Type[] typeArgs = ((ParameterizedType) genericType).getActualTypeArguments();
                if (typeArgs != null && typeArgs.length > 0) {
                    if (typeArgs.length >= 1) {
                        if (typeArgs[0] instanceof Class) {
                            keyType = (Class) typeArgs[0];
                        } else {
                            keyType = Object.class;
                        }
                    }
                    if (typeArgs.length >= 2) {
                        if (typeArgs[1] instanceof Class) {
                            valueType = (Class) typeArgs[1];
                        } else {
                            valueType = Object.class;
                        }
                    }
                }
            } else {
                keyType = Object.class;
                valueType = Object.class;
            }

            Class targetValueType = void.class;
            Class specifiedTarget = FieldMetadataHelper.getCollectionTargetType(field, true, true, false);
            if (specifiedTarget != null) {
                targetValueType = specifiedTarget;
            } else {
                targetValueType = valueType;
            }
            ClassMetadata embeddedKeyCm = null;
            if (FieldMetadataHelper.isEmbeddable(keyType)) {
                embeddedKeyCm = FieldMetadataHelper.generateEmbeddedClassMetadata(classProcessor, keyType);
            }
            ClassMetadata embeddedValueCm = null;
            if (FieldMetadataHelper.isEmbeddable(targetValueType)) {
                embeddedValueCm = FieldMetadataHelper.generateEmbeddedClassMetadata(classProcessor, targetValueType);
            }

            MapFieldFacet facet = new MapFieldFacet(keyType, embeddedKeyCm, targetValueType, embeddedValueCm);
            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(MapFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
