package com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.CollectionFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 * <p>
 * See comments in PresentationCollection ({@link PresentationCollection})
 */
public abstract class _CollectionFieldInfo extends CollectionFieldInfoBase {
    /**
     * Collection entry types: simple(embeddable included), entity
     * Collection entry creation type: simple(embeddable included), entity, join-table-entry
     * There are 4 types of entry:
     * 1. New : 1.1
     * 2. Lookup: 1.2.a, 1.2.b (code in PresentationCollection)
     * 3. Lookup with key: 1.2.c
     * 4. Basic: 1.3
     * <p>
     * The difference between the 4 types are handled in the metadata,
     */

    private final String instanceType;

    public _CollectionFieldInfo(String name, String friendlyName, boolean editable, String instanceType) {
        super(name, friendlyName, editable);
        this.instanceType = instanceType;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public abstract String getEntryType();
}
