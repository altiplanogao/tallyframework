package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.CollectionFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 *
 * See comments in PresentationCollection ({@link com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection})
 */
public class CollectionFieldInfo extends CollectionFieldInfoBase {
    /**
     * Collection entry types: simple(embeddable included), entity
     * Collection entry creation type: simple(embeddable included), entity, join-table-entry
     * There are 4 types of entry:
     * 1. New : 1.1
     * 2. Lookup: 1.2.a, 1.2.b (code in PresentationCollection)
     * 3. Lookup with key: 1.2.c
     * 4. Basic: 1.3
     *
     * The difference between the 4 types are handled in the metadata,
     */

//    private final String keyType;
    private final String entryType;
    public CollectionFieldInfo(String name, String friendlyName, boolean editable, String entryType) {
        super(name, friendlyName, editable);
        this.entryType = entryType;
    }

    public String getEntryType() {
        return entryType;
    }
}
