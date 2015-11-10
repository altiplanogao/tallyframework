package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EnumFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public final class EnumFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final Class enumerationType;

    public EnumFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        EnumFieldMetadataFacet enumFieldMetaFacet = (EnumFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Enum);
        if (null != enumFieldMetaFacet) {
            this.enumerationType = enumFieldMetaFacet.getEnumerationType();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.ENUMERATION;
    }

    public Class getEnumerationType() {
        return enumerationType;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
