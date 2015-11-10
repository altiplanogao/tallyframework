package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import static com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType.FOREIGN_KEY;

public final class ForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final Class entityType;
    private final String idField;
    private final String displayField;

    public ForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ForeignEntityFieldMetadataFacet foreignFieldFacet = (ForeignEntityFieldMetadataFacet) intermediate.getFacet(FieldFacetType.ForeignEntity);
        this.entityType = foreignFieldFacet.targetType;
        this.idField = foreignFieldFacet.idField;
        this.displayField = foreignFieldFacet.displayField;
    }

    public Class getEntityType() {
        return entityType;
    }

    public String getIdField() {
        return idField;
    }

    public String getDisplayField() {
        return displayField;
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FOREIGN_KEY;
    }
}
