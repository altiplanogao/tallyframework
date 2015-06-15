package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class GeneralFieldFacet implements IFieldFacet {
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.General;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
