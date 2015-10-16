package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class StringFieldFacet implements IFieldFacet {
    protected int length = -1;

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Basic;
    }

    @Override
    public void merge(IFieldFacet facet) {
        this.length = ((StringFieldFacet) facet).length;
    }

    public int getLength() {
        return length;
    }

    public StringFieldFacet setLength(int length) {
        this.length = length;
        return this;
    }
}
