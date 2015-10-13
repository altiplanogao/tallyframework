package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetaFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;

public class BooleanFieldMetaFacet implements IFieldMetaFacet {
    public final BooleanModel model;

    public BooleanFieldMetaFacet() {
        this(BooleanModel.YesNo);
    }

    public BooleanFieldMetaFacet(BooleanModel model) {
        this.model = model;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

    @Override
    public void merge(IFieldMetaFacet facet) {

    }
}
