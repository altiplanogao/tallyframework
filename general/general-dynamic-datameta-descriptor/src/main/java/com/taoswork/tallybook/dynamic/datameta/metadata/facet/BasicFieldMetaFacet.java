package com.taoswork.tallybook.dynamic.datameta.metadata.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class BasicFieldMetaFacet implements IFieldMetaFacet {
    protected int length = -1;

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Basic;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {
        this.length = ((BasicFieldMetaFacet)facet).length;
    }

    public int getLength() {
        return length;
    }

    public BasicFieldMetaFacet setLength(int length) {
        this.length = length;
        return this;
    }
}
