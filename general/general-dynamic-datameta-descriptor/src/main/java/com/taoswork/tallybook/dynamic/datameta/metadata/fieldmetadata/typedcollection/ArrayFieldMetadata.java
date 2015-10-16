package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class ArrayFieldMetadata extends BaseCollectionFieldMetadata {
    public ArrayFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ArrayFieldFacet arrayFieldFacet = (ArrayFieldFacet) intermediate.getFacet(FieldFacetType.Array);
    }
}
