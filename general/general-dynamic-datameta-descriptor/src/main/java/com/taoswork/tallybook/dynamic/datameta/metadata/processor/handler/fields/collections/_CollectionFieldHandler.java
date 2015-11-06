package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;
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
class _CollectionFieldHandler extends _1DCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_CollectionFieldHandler.class);
    private final ClassProcessor classProcessor;

    public _CollectionFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class collectionType = field.getType();
        if (Collection.class.isAssignableFrom(collectionType)) {
            Type genericType = field.getGenericType();

            if (GenericTypeUtility.isTypeArgumentMissing(genericType)) {
                LOGGER.info("Field '{}.{}' having Type '{}' -> '{}' ",
                    field.getDeclaringClass().getSimpleName(), field.getName(),
                    collectionType, genericType);
                LOGGER.warn("Field '{}.{}' having type '{}' should specify its type argument.",
                    field.getDeclaringClass().getSimpleName(), field.getName(), collectionType);
            }

            CollectionFieldMetadataFacet facet = getCollectionFieldMetadataFacet(field, collectionType, genericType);
            fieldMetadata.addFacet(facet);
            fieldMetadata.setTargetMetadataType(CollectionFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }

    private CollectionFieldMetadataFacet getCollectionFieldMetadataFacet(Field field, Class collectionType, Type genericType) {
        Class entryType = Object.class;
        if (genericType instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) genericType).getActualTypeArguments();
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    entryType = (Class) typeArgs[0];
                } else {
                    entryType = Object.class;
                }
            }
        } else {
            entryType = Object.class;
        }

        Class targetEntryType = void.class;
        Class specifiedTarget = FieldMetadataHelper.getCollectionTargetType(field, true, true, true);
        if (specifiedTarget != null) {
            targetEntryType = specifiedTarget;
        } else {
            targetEntryType = entryType;
        }

        Class<? extends ISimpleEntryDelegate> simpleEntryDelegate = null;
        PresentationCollection presentationCollection = field.getAnnotation(PresentationCollection.class);
        if(presentationCollection != null){
            Class<? extends ISimpleEntryDelegate> marked = presentationCollection.simpleEntryDelegate();
            if (!ISimpleEntryDelegate.class.equals(marked)){
                simpleEntryDelegate = marked;
            }
        }

        return new CollectionFieldMetadataFacet(collectionType, entryType, targetEntryType, simpleEntryDelegate);
    }
}
