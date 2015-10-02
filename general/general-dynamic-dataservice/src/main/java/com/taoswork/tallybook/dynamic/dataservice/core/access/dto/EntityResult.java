package com.taoswork.tallybook.dynamic.dataservice.core.access.dto;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/9/24.
 */
public class EntityResult<T extends Persistable> {
    String idKey;
    String idValue;
    T entity;

    public String getIdKey() {
        return idKey;
    }

    public EntityResult setIdKey(String idKey) {
        this.idKey = idKey;
        return this;
    }

    public String getIdValue() {
        return idValue;
    }

    public EntityResult setIdValue(String idValue) {
        this.idValue = idValue;
        return this;
    }

    public T getEntity() {
        return entity;
    }

    public EntityResult setEntity(T entity) {
        this.entity = entity;
        return this;
    }
}
