package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EnumFieldMetadataFacet implements IFieldMetadataFacet {
    private final Class enumerationType;

    public EnumFieldMetadataFacet(Class enumerationType) {
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
    public void merge(IFieldMetadataFacet facet) {

    }
}
