package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
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
        final Class targetEntryType;
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
            targetEntryType = specifiedTarget;
        } else {
            targetEntryType = entryType;
        }

        Class<? extends ISimpleEntryDelegate> simpleEntryDelegate = null;
        PresentationCollection presentationCollection = field.getAnnotation(PresentationCollection.class);
        CollectionModel collectionModel = CollectionModel.Unknown;
        Class joinEntity = null;
        if (presentationCollection != null) {
            Class<? extends ISimpleEntryDelegate> marked = presentationCollection.simpleEntryDelegate();
            if (!ISimpleEntryDelegate.class.equals(marked)) {
                simpleEntryDelegate = marked;
            }
            collectionModel = presentationCollection.collectionModel();
            joinEntity = presentationCollection.joinEntity();
            if (void.class.equals(joinEntity)) {
                joinEntity = null;
            }
        }
        if (collectionModel == CollectionModel.Unknown) {
            if (FieldMetadataHelper.isEmbeddable(targetEntryType)) {
                collectionModel = CollectionModel.Embeddable;
            } else if (FieldMetadataHelper.isEntity(targetEntryType)) {
                OneToMany oneToManyRelation = field.getAnnotation(OneToMany.class);
                if (oneToManyRelation != null) {
                    collectionModel = CollectionModel.Entity;
                } else {
                    ManyToMany manyToManyRelation = field.getAnnotation(ManyToMany.class);
                    if (manyToManyRelation != null) {
                        if (presentationCollection != null) {
                            if (presentationCollection.joinEntity() != void.class) {
                                collectionModel = CollectionModel.AdornedLookup;
                            } else {
                                collectionModel = CollectionModel.Lookup;
                            }
                        } else {
                            collectionModel = CollectionModel.Lookup;
                        }
                    }
                }
            } else {
                collectionModel = CollectionModel.Primitive;
            }
        }
        if (collectionModel == CollectionModel.Unknown) {
            throw new IllegalArgumentException("Unknown collection model for field: " + field);
        }
        if(CollectionModel.Primitive.equals(collectionModel)){
            if(simpleEntryDelegate == null){
                if(String.class.equals(targetEntryType)){
                    simpleEntryDelegate = StringEntryDelegate.class;
                }
            }
            if(simpleEntryDelegate == null){
                throw new IllegalArgumentException("simpleEntryDelegate couldn't be null: " + field);
            }
        }

        CollectionFieldMetadataFacet collectionFieldMetadataFacet = new CollectionFieldMetadataFacet(entryType, targetEntryType,
            simpleEntryDelegate, joinEntity, collectionModel, collectionType);
        return collectionFieldMetadataFacet;
    }
}
