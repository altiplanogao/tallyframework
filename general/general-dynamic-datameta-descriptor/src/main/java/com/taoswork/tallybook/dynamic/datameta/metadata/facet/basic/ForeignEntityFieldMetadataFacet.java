package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

public class ForeignEntityFieldMetadataFacet implements IFieldMetadataFacet {
    public final Class declaredType;
    public final Class targetType;
    public final String displayField;

    public ForeignEntityFieldMetadataFacet(Class declaredType, Class targetType,String displayField) {
        this.declaredType = declaredType;
        this.targetType = targetType;
        this.displayField = displayField;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ForeignEntity;
    }

    @Override
    public void merge(IFieldMetadataFacet facet) {

    }
}
