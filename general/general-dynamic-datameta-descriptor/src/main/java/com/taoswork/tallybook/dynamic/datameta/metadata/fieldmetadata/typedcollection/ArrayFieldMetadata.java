package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public class ArrayFieldMetadata extends BaseCollectionFieldMetadata {

    private final EntryTypeUnion entryTypeUnion;

    public ArrayFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ArrayFieldMetadataFacet facet = (ArrayFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Array);
        this.entryTypeUnion = facet.getEntryTypeUnion();
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.UNKNOWN;
    }

    public EntryTypeUnion getEntryTypeUnion() {
        return entryTypeUnion;
    }

}
