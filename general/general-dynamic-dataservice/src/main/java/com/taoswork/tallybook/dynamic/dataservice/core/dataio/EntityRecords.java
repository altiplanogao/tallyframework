package com.taoswork.tallybook.dynamic.dataservice.core.dataio;

import java.util.*;

public class EntityRecords {
    private final String entityType;
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
