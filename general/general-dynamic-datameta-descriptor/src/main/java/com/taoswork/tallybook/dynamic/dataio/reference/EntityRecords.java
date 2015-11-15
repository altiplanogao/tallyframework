package com.taoswork.tallybook.dynamic.dataio.reference;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity records for a particular entity type
 * Having id-value pairs
 */
public class EntityRecords {
    /**
     * The entities' type
     */
    private final String entityType;
    /**
     * Records with id as key
     */
    private final Map<Object, Object> entities = new HashMap<Object, Object>();

    public EntityRecords(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setRecord(Object id, Object record) {
        entities.put(id, record);
    }

    public Object getRecord(Object id) {
        return entities.get(id);
    }

    public boolean isEmpty(){
        return entities.isEmpty();
    }
}
