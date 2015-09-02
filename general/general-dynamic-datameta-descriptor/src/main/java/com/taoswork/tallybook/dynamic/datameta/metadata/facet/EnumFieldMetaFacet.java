package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EnumFieldMetaFacet implements IFieldMetaFacet {
    private final Class enumerationType;

    public EnumFieldMetaFacet(Class enumerationType){
        this.enumerationType = enumerationType;
    }

    public Class getEnumerationType() {
        return enumerationType;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Enum;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {

    }
}
