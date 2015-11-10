package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.StringFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public final class StringFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final int length;

    public StringFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        StringFieldMetadataFacet basicFieldFacet = (StringFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Basic);
        if (null != basicFieldFacet) {
            this.length = basicFieldFacet.getLength();
        } else {
            this.length = 255;
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.STRING;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
