package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldMetaFacet extends CollectionFieldMetaFacetBase {
    public ArrayFieldMetaFacet(Type genericType) {

    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Array;
    }
}
