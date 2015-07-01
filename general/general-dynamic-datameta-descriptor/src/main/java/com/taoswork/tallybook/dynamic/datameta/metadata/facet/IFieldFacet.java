package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public interface IFieldFacet {
    FieldFacetType getType();

    void merge(IFieldFacet facet);
}
