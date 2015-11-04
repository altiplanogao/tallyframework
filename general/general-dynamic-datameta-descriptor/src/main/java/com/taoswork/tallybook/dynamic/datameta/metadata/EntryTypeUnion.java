package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.EntryType;

import java.io.Serializable;

public class EntryTypeUnion implements Serializable {
    private final EntryType entryType;
    private final Class simpleType;
    private final ClassMetadata asEmbeddedClassMetadata;
    private final Class entityType;

    public EntryTypeUnion(Class eleType, ClassMetadata asEmbeddedClassMetadata) {
        if (FieldMetadataHelper.isEmbeddable(eleType)) {
            this.simpleType = null;
            this.asEmbeddedClassMetadata = asEmbeddedClassMetadata;
            this.entityType = null;
            this.entryType = EntryType.Embeddable;
        } else if (FieldMetadataHelper.isEntity(eleType)) {
            this.simpleType = null;
            this.asEmbeddedClassMetadata = null;
            this.entityType = eleType;
            this.entryType = EntryType.Entity;
        } else {
            this.simpleType = eleType;
            this.asEmbeddedClassMetadata = null;
            this.entityType = null;
            this.entryType = EntryType.Simple;
        }
    }

    public Class getSimpleType() {
        return simpleType;
    }

    public ClassMetadata getAsEmbeddedClassMetadata() {
        return asEmbeddedClassMetadata;
    }

    public Class getEntityType() {
        return entityType;
    }

    public boolean isSimple() {
        return EntryType.Simple.equals(entryType);
    }

    public boolean isEmbedded() {
        return EntryType.Embeddable.equals(entryType);
    }

    public boolean isEntity() {
        return EntryType.Entity.equals(entryType);
    }

    public EntryType getEntryType() {
        return entryType;
    }
}
