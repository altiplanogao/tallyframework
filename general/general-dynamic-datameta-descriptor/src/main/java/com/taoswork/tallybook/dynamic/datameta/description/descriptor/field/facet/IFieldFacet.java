package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/9/1.
 */
public interface IFieldFacet extends Serializable {
    FieldFacetType getType();

}
