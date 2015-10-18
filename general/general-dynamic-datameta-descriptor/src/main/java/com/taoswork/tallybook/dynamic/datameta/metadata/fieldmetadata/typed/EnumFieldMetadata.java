package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EnumFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class EnumFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final Class enumerationType;

    public EnumFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        EnumFieldFacet enumFieldMetaFacet = (EnumFieldFacet) intermediate.getFacet(FieldFacetType.Enum);
        if (null != enumFieldMetaFacet) {
            this.enumerationType = enumFieldMetaFacet.getEnumerationType();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Class getEnumerationType() {
        return enumerationType;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
