package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

public class ForeignEntityFieldMetadataFacet implements IFieldMetadataFacet {
    public final Class declaredType;
    public final Class targetType;

    public ForeignEntityFieldMetadataFacet(Class declaredType, Class targetType) {
        this.declaredType = declaredType;
        this.targetType = targetType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ForeignEntity;
    }

    @Override
    public void merge(IFieldMetadataFacet facet) {

    }
}
