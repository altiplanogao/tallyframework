package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanMode;

public class BooleanFieldMetadataFacet implements IFieldMetadataFacet {
    public final BooleanMode mode;

    public BooleanFieldMetadataFacet() {
        this(BooleanMode.YesNo);
    }

    public BooleanFieldMetadataFacet(BooleanMode mode) {
        this.mode = mode;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

}
