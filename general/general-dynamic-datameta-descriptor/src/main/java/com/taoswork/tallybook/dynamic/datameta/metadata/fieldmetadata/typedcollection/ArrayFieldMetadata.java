package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public class ArrayFieldMetadata extends BaseCollectionFieldMetadata {
    public ArrayFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ArrayFieldMetadataFacet arrayFieldFacet = (ArrayFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Array);
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.UNKNOWN;
    }
}
