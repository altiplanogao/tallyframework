package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.lang.reflect.Constructor;
import java.util.*;

public final class CollectionFieldMetadata extends CollectionLikeFieldMetadata {
    private final Class collectionImplementType;

    private final EntryTypeUnion entryTypeUnion;

    public CollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);

        CollectionFieldMetadataFacet facet = (CollectionFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Collection);

        this.entryTypeUnion = facet.getEntryTypeUnion();
        this.collectionImplementType = workOutCollectionImplementType(facet.getCollectionType());
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    public Class getCollectionImplementType() {
        return collectionImplementType;
    }

    @Override
    public EntryTypeUnion getEntryTypeUnion() {
        return entryTypeUnion;
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
