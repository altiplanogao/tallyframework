package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.EntryType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.PaleEntry;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public final class EntryTypeUnion implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntryTypeUnion.class);

    private final EntryType entryType;
    private final Class entryClass;
    private final Class<? extends IPrimitiveEntry> simpleEntryDelegate;

    public EntryTypeUnion(Class eleType, Class<? extends IPrimitiveEntry> simpleEntryDelegate) {
        entryClass = eleType;
        if (FieldMetadataHelper.isEmbeddable(eleType)) {
            this.entryType = EntryType.Embeddable;
            this.simpleEntryDelegate = null;
        } else if (FieldMetadataHelper.isEntity(eleType)) {
            this.entryType = EntryType.Entity;
            this.simpleEntryDelegate = null;
        } else {
            this.entryType = EntryType.Simple;
            if(simpleEntryDelegate == null && String.class.equals(entryClass)){
                simpleEntryDelegate = StringEntry.class;
            }

            if(simpleEntryDelegate == null){
                simpleEntryDelegate = PaleEntry.class;
                LOGGER.error("simple entry un-workable if primitiveDelegate == null ");
            }
            this.simpleEntryDelegate = simpleEntryDelegate;
        }
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public Class getEntryClass() {
        return entryClass;
    }

    public Class getPresentationClass() {
        if (isEmbeddableOrEntity()) {
            return entryClass;
        } else if (isSimple()) {
            return simpleEntryDelegate;
        }
        return null;
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

    public Class<? extends IPrimitiveEntry> getSimpleEntryDelegateClass(){
        if(isSimple()){
            return simpleEntryDelegate;
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
