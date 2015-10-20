package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

public class ExternalForeignEntityFieldMetadataFacet implements IFieldMetadataFacet {
    public final String realTargetField;
    public final Class targetType;
    public final String displayField;

    public ExternalForeignEntityFieldMetadataFacet(String realTargetField, Class targetType, String displayField) {
        this.realTargetField = realTargetField;
        this.targetType = targetType;
        this.displayField = displayField;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ExternalForeignEntity;
    }

    @Override
    public void merge(IFieldMetadataFacet facet) {

    }
}
