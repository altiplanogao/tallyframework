package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;

import java.lang.reflect.Field;

public class ExternalForeignEntityFieldFacet implements IFieldFacet {
    public final String realTargetField;
    public final Class targetType;

    public ExternalForeignEntityFieldFacet(String realTargetField, Class targetType) {
        this.realTargetField = realTargetField;
        this.targetType = targetType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.ExternalForeignEntity;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
