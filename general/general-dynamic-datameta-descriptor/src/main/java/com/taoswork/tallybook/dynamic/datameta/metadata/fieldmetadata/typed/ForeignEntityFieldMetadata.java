package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class ForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private Class entityType;

    public ForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ForeignEntityFieldFacet foreignFieldFacet = (ForeignEntityFieldFacet) intermediate.getFacet(FieldFacetType.ForeignEntity);
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
