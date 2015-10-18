package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;

import java.lang.reflect.Field;

public class ExternalForeignEntityFieldFacet implements IFieldFacet {
    public final String realTargetField;
    public final Class targetType;
    public final String displayField;

    public ExternalForeignEntityFieldFacet(String realTargetField, Class targetType, String displayField) {
        this.realTargetField = realTargetField;
        this.targetType = targetType;
        this.displayField = displayField;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ExternalForeignEntity;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
