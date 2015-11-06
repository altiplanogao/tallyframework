package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldMetadataFacet extends _1DCollectionFieldMetadataFacet {

    public ArrayFieldMetadataFacet(Class entryType, Class<? extends ISimpleEntryDelegate> simpleEntryDelegate) {
        super(entryType, entryType, simpleEntryDelegate);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Array;
    }

}
