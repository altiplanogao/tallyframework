package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.apache.commons.lang3.SerializationUtils;

import java.lang.reflect.Constructor;
import java.util.*;

public final class CollectionFieldMetadata extends BaseCollectionFieldMetadata {
    private final CollectionTypesUnion collectionTypesUnion;

    private final Class presentationCeilingClass;
    private final Class presentationClass;
    private final Class collectionImplementType;

    public CollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);

        CollectionFieldMetadataFacet facet = (CollectionFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Collection);
        this.collectionTypesUnion = SerializationUtils.clone(facet.getCollectionTypesUnion());
        this.collectionImplementType = workOutCollectionImplementType(collectionTypesUnion.getCollectionClass());

        switch (collectionTypesUnion.getCollectionModel()) {
            case Primitive:
                presentationClass = collectionTypesUnion.getEntrySimpleDelegateType();
                presentationCeilingClass = presentationClass;
                break;
            case Embeddable:
                presentationClass = collectionTypesUnion.getEntryTargetType();
                presentationCeilingClass = presentationClass;
                break;
            case Entity:
                presentationClass = collectionTypesUnion.getEntryTargetType();
                presentationCeilingClass = collectionTypesUnion.getEntryType();
                break;
            case Lookup:
                presentationClass = collectionTypesUnion.getEntryTargetType();
                presentationCeilingClass = collectionTypesUnion.getEntryType();
                break;
            case AdornedLookup:
                presentationClass = collectionTypesUnion.getEntryJoinEntityType();
                presentationCeilingClass = collectionTypesUnion.getEntryType();
                break;
            default:
                throw new IllegalArgumentException("Collection Model not specified.");
        }
        if(null == presentationClass){
            throw new IllegalArgumentException("presentationClass couldn't be null.");
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    public CollectionTypesUnion getCollectionTypesUnion() {
        return collectionTypesUnion;
    }

    @Override
    public Class getPresentationClass() {
        return presentationClass;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return presentationCeilingClass;
    }

    public Class getCollectionImplementType() {
        return collectionImplementType;
    }

    private Class workOutCollectionImplementType(Class collectionType) {
        try {
            Constructor constructor = collectionType.getConstructor(new Class[]{});
            return collectionType;
        } catch (NoSuchMethodException e) {
            //Ignore this exception, because it is not an instantiatable object.
        }
        if (List.class.equals(collectionType)) {
            return ArrayList.class;
        } else if (Set.class.equals(collectionType)) {
            return HashSet.class;
        } else if (Collection.class.equals(collectionType)) {
            return ArrayList.class;
        } else {
            return ArrayList.class;
        }
    }
}
