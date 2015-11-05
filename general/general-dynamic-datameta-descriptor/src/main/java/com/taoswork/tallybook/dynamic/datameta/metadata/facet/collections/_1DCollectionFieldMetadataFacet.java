package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/11/5.
 */
abstract class _1DCollectionFieldMetadataFacet implements IFieldMetadataFacet {
    protected final Class entryType;
    protected final Class targetEntryType;
    protected final EntryTypeUnion elementTypeUnion;

    _1DCollectionFieldMetadataFacet(Class entryType, Class targetEntryType, ClassMetadata embeddedClassMetadata) {
        this.entryType = entryType;
        this.targetEntryType = targetEntryType;
        this.elementTypeUnion = new EntryTypeUnion(targetEntryType, embeddedClassMetadata);
    }

    public EntryTypeUnion getEntryTypeUnion() {
        return elementTypeUnion;
    }
}
