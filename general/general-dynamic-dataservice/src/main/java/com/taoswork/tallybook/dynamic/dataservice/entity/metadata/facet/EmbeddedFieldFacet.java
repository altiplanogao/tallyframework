package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldFacet implements IFieldFacet{
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Embedded;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
