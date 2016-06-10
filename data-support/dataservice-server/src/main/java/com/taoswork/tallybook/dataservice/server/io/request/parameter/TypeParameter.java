package com.taoswork.tallybook.dataservice.server.io.request.parameter;

/**
 * Created by Gao Yuan on 2015/12/23.
 */
public class TypeParameter {
    protected final Class ceilingType;
    protected final Class type;

    public TypeParameter(Class ceilingType, Class type) {
        this.ceilingType = ceilingType;
        this.type = type;
    }

    public Class getCeilingType() {
        return ceilingType;
    }

    public Class getType() {
        return type;
    }
}
