package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.BooleanFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;

public class BooleanFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final BooleanModel model;

    public BooleanFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        BooleanFieldMetadataFacet booleanFieldFacet = (BooleanFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Boolean);
        if (null != booleanFieldFacet) {
            this.model = booleanFieldFacet.model;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.BOOLEAN;
    }

    public BooleanModel getModel() {
        return model;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
