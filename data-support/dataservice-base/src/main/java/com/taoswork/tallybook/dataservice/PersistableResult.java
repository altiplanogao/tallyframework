package com.taoswork.tallybook.dataservice;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/9/24.
 */
public class PersistableResult<T extends Persistable> {
    String idKey;
    String idValue;
    String name;
    T value;

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

    public String getName() {
        return name;
    }

    public PersistableResult setName(String name) {
        this.name = name;
        return this;
    }

    public T getValue() {
        return value;
    }

    public PersistableResult setValue(T value) {
        this.value = value;
        return this;
    }
}
