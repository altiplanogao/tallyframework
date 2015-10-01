package com.taoswork.tallybook.dynamic.dataservice.core.access.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity implements Serializable{
    private Class<?> entityType;
    private Class<?> entityCeilingType;
    private final Map<String, String> entity = new HashMap<String, String>();
    public final static String ENTITY_PROPERTY_NAME = "entity";

    public Class<?> getEntityType() {
        return entityType;
    }

    public Entity setEntityType(Class<?> entityType) {
        this.entityType = entityType;
        return this;
    }

    public Class<?> getEntityCeilingType() {
        return entityCeilingType;
    }

    public Entity setEntityCeilingType(Class<?> entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
        return this;
    }

    public Map<String, String> getEntity() {
        return entity;
    }

    public Entity setProperty(String key, String value){
        this.entity.put(key, value);
        return this;
    }
}
