package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;

import javax.persistence.Temporal;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public class DateFieldMetadataFacet implements IFieldMetadataFacet {
    public final DateModel model;

    public final boolean useJavaDate;

    public DateFieldMetadataFacet(DateModel model, boolean useJavaDate) {
        this.model = model;
        this.useJavaDate = useJavaDate;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Date;
    }

    @Override
    public void merge(IFieldMetadataFacet facet) {

    }
}
