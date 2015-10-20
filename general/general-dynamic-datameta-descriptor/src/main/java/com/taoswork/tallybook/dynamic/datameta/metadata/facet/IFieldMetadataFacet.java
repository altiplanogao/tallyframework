package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public interface IFieldMetadataFacet extends Serializable {
    FieldFacetType getType();

    void merge(IFieldMetadataFacet facet);
}
