package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;

import java.io.Serializable;

public class ElementTypeUnion implements Serializable {
    private final Class basicType;
    private final ClassMetadata asEmbeddedClassMetadata;
    private final Class entityType;

    public ElementTypeUnion(Class eleType, ClassMetadata asEmbeddedClassMetadata) {
        if (FieldMetadataHelper.isEmbeddable(eleType)) {
            this.basicType = null;
            this.asEmbeddedClassMetadata = asEmbeddedClassMetadata;
            this.entityType = null;
        } else if (FieldMetadataHelper.isEntity(eleType)) {
            this.basicType = null;
            this.asEmbeddedClassMetadata = null;
            this.entityType = eleType;
        } else {
            this.basicType = eleType;
            this.asEmbeddedClassMetadata = null;
            this.entityType = null;
        }
    }

    public Class getBasicType() {
        return basicType;
    }

    public ClassMetadata getAsEmbeddedClassMetadata() {
        return asEmbeddedClassMetadata;
    }

    public Class getEntityType() {
        return entityType;
    }

    public boolean isBasic() {
        return null != basicType;
    }

    public boolean isEmbedded() {
        return null != asEmbeddedClassMetadata;
    }

    public boolean isEntity() {
        return null != entityType;
    }
}
