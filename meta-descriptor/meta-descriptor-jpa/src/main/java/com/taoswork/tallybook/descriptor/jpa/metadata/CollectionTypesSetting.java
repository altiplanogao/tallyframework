package com.taoswork.tallybook.descriptor.jpa.metadata;

import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;

import java.io.Serializable;

public final class CollectionTypesSetting implements Serializable {
    protected final Class entryType;
    protected final Class entryTargetType;
    protected final Class collectionClass;
    protected final CollectionMode collectionMode;

    protected final Class<? extends IPrimitiveEntry> entryPrimitiveDelegateType;
    protected final Class entryJoinEntityType;

    public CollectionTypesSetting(Class entryType, Class entryTargetType,
                                  Class collectionClass,
                                  CollectionMode collectionMode,
                                  Class<? extends IPrimitiveEntry> entryPrimitiveDelegateType,
                                  Class entryJoinEntityType) {
        this.entryType = entryType;
        this.entryTargetType = entryTargetType;
        this.collectionClass = collectionClass;
        this.collectionMode = collectionMode;

        this.entryPrimitiveDelegateType = entryPrimitiveDelegateType;
        this.entryJoinEntityType = entryJoinEntityType;
    }

    public Class getEntryType() {
        return entryType;
    }

    public Class getEntryTargetType() {
        return entryTargetType;
    }

    public Class getCollectionClass() {
        return collectionClass;
    }

    public CollectionMode getCollectionMode() {
        return collectionMode;
    }

    public Class<? extends IPrimitiveEntry> getEntryPrimitiveDelegateType() {
        return entryPrimitiveDelegateType;
    }

    public Class getEntryJoinEntityType() {
        return entryJoinEntityType;
    }

}
