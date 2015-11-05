package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

public class ForeignEntityFieldMetadataFacet implements IFieldMetadataFacet {
    public final Class declaredType;
    public final Class targetType;
    public final String idField;
    public final String displayField;

    public ForeignEntityFieldMetadataFacet(Class declaredType, Class targetType, String idField, String displayField) {
        this.declaredType = declaredType;
        this.targetType = targetType;
        this.idField = idField;
        this.displayField = displayField;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ForeignEntity;
    }
}