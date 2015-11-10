package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldMetadataFacet implements IFieldMetadataFacet {
    public final IClassMetadata classMetadata;

    public EmbeddedFieldMetadataFacet(IClassMetadata classMetadata) {
        this.classMetadata = classMetadata;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Embedded;
    }
}
