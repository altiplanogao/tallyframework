package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.EntryType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.PaleEntryDelegate;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public final class CollectionTypesUnion implements Serializable {
    protected final Class entryType;
    protected final Class entryTargetType;
    protected final Class<? extends ISimpleEntryDelegate> entrySimpleDelegateType;
    protected final Class entryJoinEntityType;

    protected final CollectionModel collectionModel;
    private final Class collectionClass;

    public CollectionTypesUnion(Class entryType, Class entryTargetType,
                                Class<? extends ISimpleEntryDelegate> entrySimpleDelegateType,
                                Class entryJoinEntityType,
                                CollectionModel collectionModel,
                                Class collectionClass) {
        this.entryType = entryType;
        this.entryTargetType = entryTargetType;
        this.entrySimpleDelegateType = entrySimpleDelegateType;
        this.entryJoinEntityType = entryJoinEntityType;
        this.collectionModel = collectionModel;
        this.collectionClass = collectionClass;
    }

    public Class getEntryType() {
        return entryType;
    }

    public Class getEntryTargetType() {
        return entryTargetType;
    }

    public Class<? extends ISimpleEntryDelegate> getEntrySimpleDelegateType() {
        return entrySimpleDelegateType;
    }

    public Class getEntryJoinEntityType() {
        return entryJoinEntityType;
    }

    public CollectionModel getCollectionModel() {
        return collectionModel;
    }

    public Class getCollectionClass() {
        return collectionClass;
    }
}
