package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldMetadataFacet extends CollectionFieldMetadataFacetBase {
    private Type entryType;

    public ArrayFieldMetadataFacet(Type entryType) {
        this.entryType = entryType;
    }

    public Type getEntryType() {
        return entryType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Array;
    }
}
