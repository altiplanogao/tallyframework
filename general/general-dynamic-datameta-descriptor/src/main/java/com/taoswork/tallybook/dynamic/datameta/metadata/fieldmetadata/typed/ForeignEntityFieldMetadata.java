package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import static com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType.FOREIGN_KEY;

public final class ForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final Class declareType;
    private final Class targetType;
    private final String idField;
    private final String displayField;

    public ForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ForeignEntityFieldMetadataFacet foreignFieldFacet = (ForeignEntityFieldMetadataFacet) intermediate.getFacet(FieldFacetType.ForeignEntity);
        this.declareType = foreignFieldFacet.declaredType;
        this.targetType = foreignFieldFacet.targetType;
        this.idField = foreignFieldFacet.idField;
        this.displayField = foreignFieldFacet.displayField;
    }

    public Class getDeclareType() {
        return declareType;
    }

    public Class getTargetType() {
        return targetType;
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
