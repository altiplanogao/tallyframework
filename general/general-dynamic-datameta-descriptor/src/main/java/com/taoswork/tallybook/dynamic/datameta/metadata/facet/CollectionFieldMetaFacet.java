package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldMetaFacet implements IFieldMetaFacet {
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {

    }
}
