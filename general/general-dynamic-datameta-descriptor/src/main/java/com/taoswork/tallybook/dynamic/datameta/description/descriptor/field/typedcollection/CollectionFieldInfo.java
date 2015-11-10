package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.CollectionFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class CollectionFieldInfo extends CollectionFieldInfoBase {
    private final String entryType;
    public CollectionFieldInfo(String name, String friendlyName, boolean editable, String entryType) {
        super(name, friendlyName, editable);
        this.entryType = entryType;
    }

    public String getEntryType() {
        return entryType;
    }
}
