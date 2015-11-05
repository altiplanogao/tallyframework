package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacetMergeable;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class StringFieldMetadataFacet implements IFieldMetadataFacet, IFieldMetadataFacetMergeable {
    protected int length = -1;

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Basic;
    }

    @Override
    public void merge(IFieldMetadataFacet facet) {
        this.length = ((StringFieldMetadataFacet) facet).length;
    }

    public int getLength() {
        return length;
    }

    public StringFieldMetadataFacet setLength(int length) {
        this.length = length;
        return this;
    }
}
