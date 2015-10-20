package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

public class StringFacet implements IFieldFacet {
    private final int length;

    public StringFacet(int length) {
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
