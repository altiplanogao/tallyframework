package com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldFacet implements IFieldFacet {
    public final ClassMetadata embeddedClassMetadata;

    public EmbeddedFieldFacet(ClassMetadata embeddedClassMetadata) {
        this.embeddedClassMetadata = embeddedClassMetadata;
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Embedded;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }
}
