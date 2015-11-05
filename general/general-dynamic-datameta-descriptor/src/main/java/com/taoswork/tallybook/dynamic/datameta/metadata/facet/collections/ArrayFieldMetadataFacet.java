package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ArrayFieldMetadataFacet extends _1DCollectionFieldMetadataFacet {

    public ArrayFieldMetadataFacet(Class entryType, ClassMetadata embeddedClassMetadata) {
        super(entryType, entryType, embeddedClassMetadata);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Array;
    }

}
