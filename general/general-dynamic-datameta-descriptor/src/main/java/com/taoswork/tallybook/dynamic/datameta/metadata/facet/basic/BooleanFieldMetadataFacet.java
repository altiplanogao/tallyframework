package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;

public class BooleanFieldMetadataFacet implements IFieldMetadataFacet {
    public final BooleanModel model;

    public BooleanFieldMetadataFacet() {
        this(BooleanModel.YesNo);
    }

    public BooleanFieldMetadataFacet(BooleanModel model) {
        this.model = model;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

}
