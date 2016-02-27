package com.taoswork.tallybook.dataservice.server.io.response.result;

/**
 */
public class EntityInstanceResult {
    String idKey;
    String idValue;

    String name;
    Object bean;

    public Object getBean() {
        return bean;
    }

    public EntityInstanceResult setBean(Object bean) {
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

    public String getName() {
        return name;
    }

    public EntityInstanceResult setName(String name) {
        this.name = name;
        return this;
    }
}
