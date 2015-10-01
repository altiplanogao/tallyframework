package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public class EntityTypeParameter {
    String typeName;
    Class ceilingType;
    Class type;

    public String getTypeName() {
        return typeName;
    }

    public EntityTypeParameter setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public Class getCeilingType() {
        return ceilingType;
    }

    public EntityTypeParameter setCeilingType(Class ceilingType) {
        this.ceilingType = ceilingType;
        return this;
    }

    public Class getType() {
        return type;
    }

    public EntityTypeParameter setType(Class type) {
        this.type = type;
        return this;
    }

}
