package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateMode;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public class DateFieldMetadataFacet implements IFieldMetadataFacet {
    public final DateMode mode;
    public final DateCellMode cellMode;

    public final boolean useJavaDate;

    public DateFieldMetadataFacet(DateMode mode, DateCellMode cellMode, boolean useJavaDate) {
        this.mode = mode;
        this.cellMode = cellMode;
        this.useJavaDate = useJavaDate;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Date;
    }

}
