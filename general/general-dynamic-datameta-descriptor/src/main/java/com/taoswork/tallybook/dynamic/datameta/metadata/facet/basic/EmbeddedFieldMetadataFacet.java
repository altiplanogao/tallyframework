package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldMetadataFacet implements IFieldMetadataFacet {
    public final ClassMetadata embeddedClassMetadata;

    public EmbeddedFieldMetadataFacet(ClassMetadata embeddedClassMetadata) {
        this.embeddedClassMetadata = embeddedClassMetadata;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Embedded;
    }
}
