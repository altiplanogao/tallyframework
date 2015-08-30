package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public interface IFieldFacet extends Serializable{
    FieldFacetType getType();

    void merge(IFieldFacet facet);
}
