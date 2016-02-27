package com.taoswork.tallybook.dataservice.server.io.request.parameter;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public class EntityTypeParameter extends TypeParameter {
    String typeName;

    public EntityTypeParameter(Class ceilingType, Class type) {
        super(ceilingType, type);
    }

    public String getTypeName() {
        return typeName;
    }

    public EntityTypeParameter setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getEntityUri() {
        return "/" + this.typeName;
    }

}
