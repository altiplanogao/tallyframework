package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetaFacet;

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
