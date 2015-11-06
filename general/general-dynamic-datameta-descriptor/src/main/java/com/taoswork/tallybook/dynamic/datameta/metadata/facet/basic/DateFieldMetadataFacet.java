package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public class DateFieldMetadataFacet implements IFieldMetadataFacet {
    public final DateModel model;
    public final DateCellModel cellModel;

    public final boolean useJavaDate;

    public DateFieldMetadataFacet(DateModel model, DateCellModel cellModel, boolean useJavaDate) {
        this.model = model;
        this.cellModel = cellModel;
        this.useJavaDate = useJavaDate;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Date;
    }

}
