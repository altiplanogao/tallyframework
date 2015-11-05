package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldMetadataFacet extends _1DCollectionFieldMetadataFacet {

    private final Class collectionType;

    public CollectionFieldMetadataFacet(Class collectionType, Class entryType, Class targetEntryType, ClassMetadata embeddedClassMetadata) {
        super(entryType, targetEntryType, embeddedClassMetadata);
        this.collectionType = collectionType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    public Class getCollectionType() {
        return collectionType;
    }
}
