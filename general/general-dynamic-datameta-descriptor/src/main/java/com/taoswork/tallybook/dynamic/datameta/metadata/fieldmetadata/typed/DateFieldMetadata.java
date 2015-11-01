package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.DateFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public class DateFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final DateModel model;
    private final DateCellModel cellModel;
    private final boolean useJavaDate;

    public DateFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        DateFieldMetadataFacet booleanFieldFacet = (DateFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Date);
        if (null != booleanFieldFacet) {
            this.model = booleanFieldFacet.model;
            this.cellModel = booleanFieldFacet.cellModel;
            this.useJavaDate = booleanFieldFacet.useJavaDate;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.DATE;
    }

    public DateModel getModel() {
        return model;
    }

    public DateCellModel getCellModel() {
        return cellModel;
    }

    public boolean isUseJavaDate() {
        return useJavaDate;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
