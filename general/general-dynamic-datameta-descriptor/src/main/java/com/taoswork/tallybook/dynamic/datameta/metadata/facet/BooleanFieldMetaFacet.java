package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

public class BooleanFieldMetaFacet implements IFieldMetaFacet{
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {

    }
}
