package com.taoswork.tallybook.dynamic.dataio.reference;

/**
 * Created by Gao Yuan on 2015/11/26.
 */
public class ObjectResult {
    String name;
    Object value;

    public String getName() {
        return name;
    }

    public ObjectResult setName(String name) {
        this.name = name;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public ObjectResult setValue(Object value) {
        this.value = value;
        return this;
    }
}
