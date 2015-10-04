package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/10/3.
 */
public class BasicFacetInfo implements IFieldFacet{
    private final boolean required;
    private final int length;

    public BasicFacetInfo(boolean required, int length){
        this.required = required;
        this.length = length;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Basic;
    }

    public boolean isRequired() {
        return required;
    }

    public int getLength() {
        return length;
    }
}
