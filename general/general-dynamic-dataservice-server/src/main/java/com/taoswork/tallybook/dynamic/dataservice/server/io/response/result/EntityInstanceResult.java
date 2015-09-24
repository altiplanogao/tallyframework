package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

/**
 */
public class EntityInstanceResult {
    Object data;
    String idKey;
    String idValue;

    public Object getData() {
        return data;
    }

    public EntityInstanceResult setData(Object data) {
        this.data = data;
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
}
