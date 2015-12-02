package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 */
public class EntityInstanceResult {
    String dataName;
    String idKey;
    String idValue;
    Persistable bean;

    public Persistable getBean() {
        return bean;
    }

    public EntityInstanceResult setBean(Persistable bean) {
        this.bean = bean;
        return this;
    }

    public String getIdKey() {
        return idKey;
    }

    public EntityInstanceResult setIdKey(String idKey) {
        this.idKey = idKey;
        return this;
    }

    public String getIdValue() {
        return idValue;
    }

    public EntityInstanceResult setIdValue(String idValue) {
        this.idValue = idValue;
        return this;
    }

    public String getDataName() {
        return dataName;
    }

    public EntityInstanceResult setDataName(String dataName) {
        this.dataName = dataName;
        return this;
    }
}
