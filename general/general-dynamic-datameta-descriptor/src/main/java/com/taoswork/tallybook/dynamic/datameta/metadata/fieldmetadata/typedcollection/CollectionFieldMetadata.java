package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesSetting;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.apache.commons.lang3.SerializationUtils;

import java.lang.reflect.Constructor;
import java.util.*;

public final class CollectionFieldMetadata extends BaseCollectionFieldMetadata {
    private final CollectionTypesSetting collectionTypesSetting;

    private final Class presentationCeilingClass;
    private final Class presentationClass;
    private final Class collectionImplementType;

    public CollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);

        CollectionFieldMetadataFacet facet = (CollectionFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Collection);
        this.collectionTypesSetting = SerializationUtils.clone(facet.getCollectionTypesSetting());
        this.collectionImplementType = workOutCollectionImplementType(collectionTypesSetting.getCollectionClass());

        switch (collectionTypesSetting.getCollectionMode()) {
            case Primitive:
                presentationClass = collectionTypesSetting.getEntryPrimitiveDelegateType();
                presentationCeilingClass = presentationClass;
                break;
            case Basic:
                presentationClass = collectionTypesSetting.getEntryTargetType();
                presentationCeilingClass = presentationClass;
                break;
            case Entity:
                presentationClass = collectionTypesSetting.getEntryTargetType();
                presentationCeilingClass = collectionTypesSetting.getEntryType();
                break;
            case Lookup:
                presentationClass = collectionTypesSetting.getEntryTargetType();
                presentationCeilingClass = collectionTypesSetting.getEntryType();
                break;
            case AdornedLookup:
                presentationClass = collectionTypesSetting.getEntryJoinEntityType();
                presentationCeilingClass = collectionTypesSetting.getEntryType();
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

    public CollectionTypesSetting getCollectionTypesSetting() {
        return collectionTypesSetting;
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
