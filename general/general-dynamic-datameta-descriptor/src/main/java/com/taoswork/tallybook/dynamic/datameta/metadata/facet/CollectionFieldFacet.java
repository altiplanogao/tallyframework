package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldFacet implements IFieldFacet {
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
