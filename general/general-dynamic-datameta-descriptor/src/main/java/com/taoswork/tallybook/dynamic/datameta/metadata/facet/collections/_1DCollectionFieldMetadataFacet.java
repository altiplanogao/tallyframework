package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/11/5.
 */
abstract class _1DCollectionFieldMetadataFacet implements IFieldMetadataFacet {
    protected final Class entryType;
    protected final Class targetEntryType;
    protected final EntryTypeUnion elementTypeUnion;

    _1DCollectionFieldMetadataFacet(Class entryType, Class targetEntryType, Class<? extends ISimpleEntryDelegate> simpleEntryDelegate) {
        this.entryType = entryType;
        this.targetEntryType = targetEntryType;
        this.elementTypeUnion = new EntryTypeUnion(targetEntryType, simpleEntryDelegate);
    }

    public EntryTypeUnion getEntryTypeUnion() {
        return elementTypeUnion;
    }
}
