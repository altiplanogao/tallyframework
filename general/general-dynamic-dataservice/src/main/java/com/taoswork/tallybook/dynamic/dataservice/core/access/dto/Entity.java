package com.taoswork.tallybook.dynamic.dataservice.core.access.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity implements Serializable{
    private String entityType;
    private String entityCeilingType;
    private final Map<String, String> entity = new HashMap<String, String>();
    public final static String ENTITY_PROPERTY_NAME = "entity";

    public String getEntityType() {
        return entityType;
    }

    public Entity setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public Entity setEntityType(Class entityType) {
        return this.setEntityType(entityType.getName());
    }

    public String getEntityCeilingType() {
        return entityCeilingType;
    }

    public Entity setEntityCeilingType(String entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
        return this;
    }

    public Entity setEntityCeilingType(Class entityCeilingType) {
        return this.setEntityCeilingType(entityCeilingType.getName());
    }

    public Map<String, String> getEntity() {
        return entity;
    }

    public Entity setProperty(String key, String value){
        this.entity.put(key, value);
        return this;
    }
}
