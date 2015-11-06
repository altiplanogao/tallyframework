package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldMetadataFacet extends _1DCollectionFieldMetadataFacet {

    public ArrayFieldMetadataFacet(Class entryType) {
        super(entryType, entryType);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Array;
    }

}
