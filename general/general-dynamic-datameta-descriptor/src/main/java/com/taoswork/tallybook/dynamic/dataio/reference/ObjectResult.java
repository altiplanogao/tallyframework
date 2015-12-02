package com.taoswork.tallybook.dynamic.dataio.reference;

/**
 * Created by Gao Yuan on 2015/11/26.
 */
public class ObjectResult {
    String entityName;
    Object entity;

    public String getEntityName() {
        return entityName;
    }

    public ObjectResult setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public Object getEntity() {
        return entity;
    }

    public ObjectResult setEntity(Object entity) {
        this.entity = entity;
        return this;
    }
}
