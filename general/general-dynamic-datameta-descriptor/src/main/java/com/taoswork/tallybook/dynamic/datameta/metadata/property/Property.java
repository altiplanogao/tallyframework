package com.taoswork.tallybook.dynamic.datameta.metadata.property;

public class Property {
    public String key;
    public Object value;

    public Property() {
    }

    public Property(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Property setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Property setValue(Object value) {
        this.value = value;
        return this;
    }
}
