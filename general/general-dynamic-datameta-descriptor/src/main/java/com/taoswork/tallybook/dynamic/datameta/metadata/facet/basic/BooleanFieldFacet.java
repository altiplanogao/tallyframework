package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;

public class BooleanFieldFacet implements IFieldFacet {
    public final BooleanModel model;

    public BooleanFieldFacet() {
        this(BooleanModel.YesNo);
    }

    public BooleanFieldFacet(BooleanModel model) {
        this.model = model;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
