package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class GeneralFieldMetaFacet implements IFieldMetaFacet {
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.General;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {

    }
}
