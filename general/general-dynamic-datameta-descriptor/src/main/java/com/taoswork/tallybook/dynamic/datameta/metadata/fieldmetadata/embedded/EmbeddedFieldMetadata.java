package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EmbeddedFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public final class EmbeddedFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final IClassMetadata classMetadata;

    public EmbeddedFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);

        EmbeddedFieldMetadataFacet embeddedFieldFacet = (EmbeddedFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Embedded);
        this.classMetadata = embeddedFieldFacet.classMetadata;
    }

    public IClassMetadata getClassMetadata() {
        return classMetadata;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.EMBEDDABLE;
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }
}
