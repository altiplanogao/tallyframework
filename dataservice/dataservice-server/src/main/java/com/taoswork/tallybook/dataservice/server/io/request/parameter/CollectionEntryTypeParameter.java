package com.taoswork.tallybook.dataservice.server.io.request.parameter;

/**
 * Created by Gao Yuan on 2015/12/16.
 */
public class CollectionEntryTypeParameter extends TypeParameter {
    private final Class hostEntityType;
    private final String fieldName;

    public CollectionEntryTypeParameter(Class hostEntityType, String fieldName, Class ceilingType, Class type) {
        super(ceilingType, type);
        this.hostEntityType = hostEntityType;
        this.fieldName = fieldName;
    }

}
