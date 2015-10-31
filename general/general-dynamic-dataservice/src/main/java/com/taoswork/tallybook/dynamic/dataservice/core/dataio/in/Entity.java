package com.taoswork.tallybook.dynamic.dataservice.core.dataio.in;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity implements Serializable{
    private Integer timezoneOffset;
    private Class<? extends Persistable> entityType;
    private Class<? extends Persistable> entityCeilingType;
    private final Map<String, String> entity = new HashMap<String, String>();
    public final static String ENTITY_PROPERTY_NAME = "entity";

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Class<? extends Persistable> getEntityType() {
        return entityType;
    }

    public Entity setEntityType(Class<? extends Persistable> entityType) {
        this.entityType = entityType;
        return this;
    }

    public Class<? extends Persistable> getEntityCeilingType() {
        return entityCeilingType;
    }

    public Entity setEntityCeilingType(Class<? extends Persistable> entityCeilingType) {
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
