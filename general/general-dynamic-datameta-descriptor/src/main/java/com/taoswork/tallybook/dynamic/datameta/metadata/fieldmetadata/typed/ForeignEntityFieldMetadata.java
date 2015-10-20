package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public class ForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private Class entityType;

    public ForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ForeignEntityFieldMetadataFacet foreignFieldFacet = (ForeignEntityFieldMetadataFacet) intermediate.getFacet(FieldFacetType.ForeignEntity);
        BasicFieldMetadataObject bfmo = intermediate.getBasicFieldMetadataObject();
        if(FieldType.UNKNOWN == bfmo.getFieldType()){
            bfmo.setFieldType(FieldType.FOREIGN_KEY);
        }
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
