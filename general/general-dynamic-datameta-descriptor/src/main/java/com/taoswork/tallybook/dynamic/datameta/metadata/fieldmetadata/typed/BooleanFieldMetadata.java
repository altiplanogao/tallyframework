package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.BooleanFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;

public class BooleanFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final BooleanModel model;

    public BooleanFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        BooleanFieldFacet booleanFieldFacet = (BooleanFieldFacet) intermediate.getFacet(FieldFacetType.Boolean);
        if (null != booleanFieldFacet) {
            this.model = booleanFieldFacet.model;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public BooleanModel getModel() {
        return model;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
