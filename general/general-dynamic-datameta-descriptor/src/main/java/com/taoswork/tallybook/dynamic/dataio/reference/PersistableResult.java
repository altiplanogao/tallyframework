package com.taoswork.tallybook.dynamic.dataio.reference;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/9/24.
 */
public class PersistableResult<T extends Persistable> {
    String idKey;
    String idValue;
    String entityName;
    T entity;

    public String getIdKey() {
        return idKey;
    }

    public PersistableResult setIdKey(String idKey) {
        this.idKey = idKey;
        return this;
    }

    public String getIdValue() {
        return idValue;
    }

    public PersistableResult setIdValue(String idValue) {
        this.idValue = idValue;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public PersistableResult setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public T getEntity() {
        return entity;
    }

    public PersistableResult setEntity(T entity) {
        this.entity = entity;
        return this;
    }
}
