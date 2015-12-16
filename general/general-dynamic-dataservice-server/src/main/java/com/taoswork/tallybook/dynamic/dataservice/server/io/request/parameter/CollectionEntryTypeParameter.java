package com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter;

/**
 * Created by Gao Yuan on 2015/12/16.
 */
public class CollectionEntryTypeParameter {
    private final Class hostEntityType;
    private final String fieldName;

    private final Class ceilingType;
    private final Class type;

    public CollectionEntryTypeParameter(Class hostEntityType, String fieldName, Class ceilingType, Class type) {
        this.hostEntityType = hostEntityType;
        this.fieldName = fieldName;
        this.ceilingType = ceilingType;
        this.type = type;
    }

    public Class getCeilingType() {
        return ceilingType;
    }

//    public CollectionEntryTypeParameter setCeilingType(Class ceilingType) {
//        this.ceilingType = ceilingType;
//        return this;
//    }

    public Class getType() {
        return type;
    }

//    public CollectionEntryTypeParameter setType(Class type) {
//        this.type = type;
//        return this;
//    }
}
