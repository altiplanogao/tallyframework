package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;

public class ForeignEntityFieldFacet implements IFieldFacet {
    public final Class declaredType;
    public final Class targetType;

    public ForeignEntityFieldFacet(Class declaredType, Class targetType) {
        this.declaredType = declaredType;
        this.targetType = targetType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ForeignEntity;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
