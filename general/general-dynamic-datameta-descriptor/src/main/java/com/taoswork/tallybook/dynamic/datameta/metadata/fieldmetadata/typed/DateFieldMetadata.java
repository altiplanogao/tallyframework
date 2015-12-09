package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.DateFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateMode;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public final class DateFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final DateMode mode;
    private final DateCellMode cellMode;
    private final boolean useJavaDate;

    public DateFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        DateFieldMetadataFacet booleanFieldFacet = (DateFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Date);
        if (null != booleanFieldFacet) {
            this.mode = booleanFieldFacet.mode;
            this.cellMode = booleanFieldFacet.cellMode;
            this.useJavaDate = booleanFieldFacet.useJavaDate;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.DATE;
    }

    public DateMode getMode() {
        return mode;
    }

    public DateCellMode getCellMode() {
        return cellMode;
    }

    public boolean isUseJavaDate() {
        return useJavaDate;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
