package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

public class StringFacetInfo implements IFieldFacet {
    private final int length;

    public StringFacetInfo(int length) {
        this.length = length;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Basic;
    }

    public int getLength() {
        return length;
    }
}
