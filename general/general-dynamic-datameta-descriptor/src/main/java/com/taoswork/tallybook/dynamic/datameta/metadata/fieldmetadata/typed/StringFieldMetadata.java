package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.StringFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class StringFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final int length;

    public StringFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        StringFieldFacet basicFieldFacet = (StringFieldFacet) intermediate.getFacet(FieldFacetType.Basic);
        if (null != basicFieldFacet) {
            this.length = basicFieldFacet.getLength();
        } else {
            this.length = 255;
        }
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
