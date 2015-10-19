package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EmbeddedFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public class EmbeddedFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private ClassMetadata classMetadata;

    public EmbeddedFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        this.basicFieldMetadataObject.setFieldTypeIfUnknown(FieldType.EMBEDDABLE);

        EmbeddedFieldFacet embeddedFieldFacet = (EmbeddedFieldFacet) intermediate.getFacet(FieldFacetType.Embedded);
        this.classMetadata = embeddedFieldFacet.embeddedClassMetadata;
        classMetadata.publishReferencedEntityMetadatas(null);
    }

    public ClassMetadata getClassMetadata() {
        return classMetadata;
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }
}
