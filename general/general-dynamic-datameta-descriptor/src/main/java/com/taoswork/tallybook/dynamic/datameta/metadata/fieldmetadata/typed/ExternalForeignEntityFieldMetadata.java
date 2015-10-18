package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class ExternalForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private Class entityType;

    public ExternalForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ExternalForeignEntityFieldFacet foreignFieldFacet = (ExternalForeignEntityFieldFacet) intermediate.getFacet(FieldFacetType.ExternalForeignEntity);
        this.entityType = foreignFieldFacet.targetType;
    }

    public Class getEntityType() {
        return entityType;
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }
}
