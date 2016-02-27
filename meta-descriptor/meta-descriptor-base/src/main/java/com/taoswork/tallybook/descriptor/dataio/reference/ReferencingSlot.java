package com.taoswork.tallybook.descriptor.dataio.reference;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/11/5.
 */

/**
 * A field of an object is referencing another entity
 */
class ReferencingSlot {
    private final Object holder;
    private final Field holdingField;
    private final EntityReference entityReference;

    public ReferencingSlot(Object holder, Field holdingField, EntityReference entityReference) {
        this.holder = holder;
        this.holdingField = holdingField;
        this.entityReference = entityReference;
    }

    public EntityReference getEntityReference() {
        return entityReference;
    }

    public void setRecord(Object entityRecord) throws IllegalAccessException {
        holdingField.set(holder, entityRecord);
    }
}
