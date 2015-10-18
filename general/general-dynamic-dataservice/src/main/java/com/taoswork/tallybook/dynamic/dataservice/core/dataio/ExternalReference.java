package com.taoswork.tallybook.dynamic.dataservice.core.dataio;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Field;
import java.util.*;

public class ExternalReference {
    private static class ReferencingSlot{
        final Object holder;
        final Field holdingField;

        public ReferencingSlot(Object holder, Field holdingField) {
            this.holder = holder;
            this.holdingField = holdingField;
        }
    }
    private final Map<PersistableReference, Object> references = new HashMap();
    private final List<ReferencingSlot> referencingSlots = new ArrayList<ReferencingSlot>();

    public void publishReference(Object holder, Field holdingField, Class entityType, Object id){
        PersistableReference pr = new PersistableReference(entityType.getName(), id);
        references.put(pr, null);

        referencingSlots.add(new ReferencingSlot(holder, holdingField));
    }

    public boolean hasReference(){
        return !referencingSlots.isEmpty();
    }
}
