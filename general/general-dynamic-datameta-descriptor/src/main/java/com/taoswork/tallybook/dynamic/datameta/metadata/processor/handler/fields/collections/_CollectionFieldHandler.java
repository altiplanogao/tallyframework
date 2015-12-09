package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntry;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
        final Class entryType;
        final Class entryTargetType;
        if (genericType instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) genericType).getActualTypeArguments();
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    entryType = (Class) typeArgs[0];
                } else {
                    entryType = Object.class;
                }
            } else {
                entryType = Object.class;
            }
        } else {
            entryType = Object.class;
        }
        final Class specifiedTarget = FieldMetadataHelper.getCollectionTargetType(field, true, true, true);
        if (specifiedTarget != null) {
            entryTargetType = specifiedTarget;
        } else {
            entryTargetType = entryType;
        }

        Class<? extends IPrimitiveEntry> primitiveEntryDelegate = null;
        PresentationCollection presentationCollection = field.getAnnotation(PresentationCollection.class);
        CollectionMode collectionMode = CollectionMode.Unknown;
        Class joinEntity = null;
        if (presentationCollection != null) {
            Class<? extends IPrimitiveEntry> marked = presentationCollection.primitiveDelegate();
            if (!IPrimitiveEntry.class.equals(marked)) {
                primitiveEntryDelegate = marked;
            }
            collectionMode = presentationCollection.collectionMode();
            joinEntity = presentationCollection.joinEntity();
            if (void.class.equals(joinEntity)) {
                joinEntity = null;
            }
        }
        if (collectionMode == CollectionMode.Unknown) {
            if (FieldMetadataHelper.isEmbeddable(entryTargetType)) {
                collectionMode = CollectionMode.Basic;
            } else if (FieldMetadataHelper.isEntity(entryTargetType)) {
                OneToMany oneToManyRelation = field.getAnnotation(OneToMany.class);
                if (oneToManyRelation != null) {
                    collectionMode = CollectionMode.Entity;
                } else {
                    ManyToMany manyToManyRelation = field.getAnnotation(ManyToMany.class);
                    if (manyToManyRelation != null) {
                        if (presentationCollection != null) {
                            if (presentationCollection.joinEntity() != void.class) {
                                collectionMode = CollectionMode.AdornedLookup;
                            } else {
                                collectionMode = CollectionMode.Lookup;
                            }
                        } else {
                            collectionMode = CollectionMode.Lookup;
                        }
                    }
                }
            } else {
                collectionMode = CollectionMode.Primitive;
            }
        }
        if (collectionMode == CollectionMode.Unknown) {
            throw new IllegalArgumentException("Unknown collection mode for field: " + field);
        }
        if(CollectionMode.Primitive.equals(collectionMode)){
            if(primitiveEntryDelegate == null){
                if(String.class.equals(entryTargetType)){
                    primitiveEntryDelegate = StringEntry.class;
                }
            }
            if(primitiveEntryDelegate == null){
                throw new IllegalArgumentException("primitiveDelegate couldn't be null: " + field);
            }
        }

        CollectionFieldMetadataFacet collectionFieldMetadataFacet = new CollectionFieldMetadataFacet(
            entryType, entryTargetType, collectionType, collectionMode,
            primitiveEntryDelegate, joinEntity);
        return collectionFieldMetadataFacet;
    }
}
