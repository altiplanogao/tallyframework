package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.EntryType;

import java.io.Serializable;

public class EntryTypeUnion implements Serializable {
    private final EntryType entryType;
    private final Class entryClass;

    public EntryTypeUnion(Class eleType) {
        entryClass = eleType;
        if (FieldMetadataHelper.isEmbeddable(eleType)) {
            this.entryType = EntryType.Embeddable;
        } else if (FieldMetadataHelper.isEntity(eleType)) {
            this.entryType = EntryType.Entity;
        } else {
            this.entryType = EntryType.Simple;
        }
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public Class getEntryClass() {
        return entryClass;
    }

    public boolean isSimple() {
        return EntryType.Simple.equals(entryType);
    }

    public boolean isEmbeddable() {
        return EntryType.Embeddable.equals(entryType);
    }

    public boolean isEntity() {
        return EntryType.Entity.equals(entryType);
    }

    public boolean isEmbeddableOrEntity(){
        return EntryType.Embeddable.equals(entryType) || EntryType.Entity.equals(entryType);
    }

    public Class getAsSimpleClass() {
        if(isSimple()){
            return entryClass;
        }
        return null;
    }
    public Class getAsEmbeddableClass() {
        if(isEmbeddable()){
            return entryClass;
        }
        return null;
    }
    public Class getAsEntityClass() {
        if(isEntity()){
            return entryClass;
        }
        return null;
    }
}
